package controllers;
// read files on systems or in .jar
// https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder
// https://mkyong.com/java/java-read-a-file-from-resources-folder/
// https://stackoverflow.com/questions/69224931/file-separator-and-work-differently-in-a-runnable-jar
// linux issue reading files in jar for audio streams
// https://stackoverflow.com/questions/20580025/java-io-ioexception-mark-reset-not-supported-java-audio-input-stream-buffered
// audio info
// https://www.baeldung.com/java-play-sound
// File to Path and Path to File
// https://mkyong.com/java/java-convert-file-to-path/
// resource to file copy
// https://stackoverflow.com/questions/10308221/how-to-copy-file-inside-jar-to-outside-the-jar/44077426#44077426


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import core.Monster;
import core.Player;
import core.Weapon;
import utils.CsvReadException;
import utils.Constants;

public class LoadSave {
    
    // external Paths
    public static Path ext_data_folder_path;

    // game objects
    public static Map<UUID, ArrayList<Object>> players = new LinkedHashMap<>();
    public static ArrayList<UUID> players_keys = new ArrayList<>();
    public static ArrayList<Monster> monsters = new ArrayList<>();
    public static MusicPlayer music;

    // file lists
    public static ArrayList<Weapon> loaded_weapons = new ArrayList<>();
    public static ArrayList<Player> loaded_players = new ArrayList<>();
    public static ArrayList<Monster> loaded_monsters = new ArrayList<>();
    public static ArrayList<BufferedImage> loaded_backgrounds = new ArrayList<>();
    
    // create dir for files that need editing (jar uneditable)
    // must be external dir so both normal and jar exe work
    public static String createGameDirs() throws IOException {
        System.out.println();
        try {
            File new_dir = new File(Constants.MAIN_DIR);
            new_dir.mkdir();

            File data_dir = new File(new_dir.toPath().resolve(Constants.SUB_DIR).toString());
            if(data_dir.mkdir())
                System.out.println("Directory created: " + data_dir.getPath());
            else System.out.println("Directory exists: " + data_dir.getPath());
            LoadSave.ext_data_folder_path = data_dir.toPath();
            return "Directory Loaded, " + data_dir.getPath();

        } catch (SecurityException e) {
            final String msg = "createConfDir: dir - Permission Denied.";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
            throw new IOException(msg, e);
        }
    }

    // copy ALL program resources to external dir (Jar/nonJar)
    // Jar(jar:file:)   bin/res/data/weapons.csv
    // nonJar(file:)    res/data/weapons.csv
    // Resources in Jar are not files in hierarchy. They are zipped.
    // jar:file:/FULL/PATH/TO/jarName.jar!/PACKAGE/HIERARCHY/TO/CLASS/className.class
    public static void createGameFiles() {
        System.out.println();
        int data_fails = createFiles(Constants.DATA_RES, ext_data_folder_path, true);
        if(data_fails != 0)
            System.out.println(Constants.ANSI_RED + "Data files failed to copy: " + Constants.ANSI_RESET + data_fails);
        if(data_fails == 0)
            System.out.println(Constants.ANSI_GREEN + "All files copied successfully or already exist." + Constants.ANSI_RESET);
    }

    // helper for createGameFiles method (Jar/nonJar)
    // uses copy and getResource methods below
    private static int createFiles(String resource, Path dest_folder, Boolean overwrite) {
        int counter = 0;
        List<Path> paths = getResourcePaths(resource);
        for(var path : paths) {
            String filename = path.getFileName().toString();
            InputStream source = getContext().getResourceAsStream(resource + filename);
            Path destination = dest_folder.resolve(filename);
            //skip if file already exists
            if(Files.notExists(destination))
                if(!copy(source, destination, overwrite))
                    counter++;
        }
        return counter;
    }

    // copy resource file to external dir (Jar/nonJar)
    public static boolean copy(InputStream source , Path destination, Boolean overwrite) {
        boolean success = false;
        System.out.println("Copying ----> " + source + "\n\tto -> " + destination);

        try {
            if(overwrite)
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            else
                Files.copy(source, destination);
            success = true;
        } catch (IOException ex) {
            System.out.println("copy: IO could not copy");
        } catch (SecurityException e) {
            System.out.println("copy: file - Permission Denied.");
        } catch (NullPointerException e) {
            System.out.println("copy: source is null");
        }
        return success;
    }
    
    //gets all paths from resource folder (Jar/nonJar)
    public static List<Path> getResourcePaths(String folder) {
        List<Path> result = null;
        URI uri = null;
        Path dirPath;
        try {
            // if this works, not in JAR
            uri = getContext().getResource(folder).toURI();
            dirPath = Paths.get(uri);
            result = Files.walk(dirPath)
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());
        } catch (FileSystemNotFoundException e) {
            // If this is thrown, then we are running the JAR directly
            try (FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                result = Files.walk(fs.getPath(folder))
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
            } catch (IOException ex) {
                System.out.println("getResourcePaths: IN JAR: parse issue");
            }
        } catch (URISyntaxException e) {
            System.out.println("getResourcePaths: URI parse issue");
        } catch (IOException e) {
            System.out.println("getResourcePaths: NOT JAR: parse issue");  
        }
        return result;
    }

    // get current thread context of a resource (Jar/nonJar)
    public static ClassLoader getContext() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    // save external players file (Jar are read-only)
    // String name, String avatar_name, Weapon weapon(name,dice,bonus,range), int hp, int ac, int str, int dex, int con
    public static void savePlayer(Player player) throws IOException {
        String player_data =    player.getName() +","+
                                player.getAvatar().getFileName() +","+
                                player.getWeapon().getName() +","+
                                player.getWeapon().getDiceType() +","+
                                player.getWeapon().getBonus() +","+
                                player.getWeapon().getRange() +","+
                                player.getHP() +","+
                                player.getAC() +","+
                                player.getSTR() +","+
                                player.getDEX() +","+
                                player.getCON();
        try(PrintWriter writer = new PrintWriter(ext_data_folder_path.resolve(player.getName() + Constants.SAVE_EXT).toString(), StandardCharsets.UTF_8)) {
            writer.println(player_data);
            LoadSave.loaded_players.add(player); //update list of saved player files
            System.out.println(Constants.ANSI_GREEN + "Player data successfully saved to file." + Constants.ANSI_RESET);
        } catch (IOException e) {
            final String msg = "savePlayer: Could not save to player file.";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
            throw new IOException(msg, e);
        }
    }

    public static String loadPlayers() throws IOException {
        
        List<String> file_names = new ArrayList<>();

        // Get all files in data directory
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(ext_data_folder_path)) {
            for (Path path : directoryStream) {
                file_names.add(path.getFileName().toString());
            }
        } catch (IOException e) {
            final String msg = "loadPlayers: Error getting files in directory";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
            throw new IOException(msg, e);
        }

        // Load each data_file
        for(String file : file_names) {
            try (InputStream file_stream = new FileInputStream(ext_data_folder_path.resolve(file).toFile())) {
                InputStreamReader stream_reader = new InputStreamReader(file_stream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(stream_reader);
                String line = null;
                while((line = reader.readLine()) != null) {
                    LoadSave.loaded_players.add(Player.loadFromCsv(line));
                }
                System.out.println(Constants.ANSI_GREEN + "Player data successfully loaded from file." + Constants.ANSI_RESET);
            } catch (IOException e) {
                final String msg = "loadPlayers: File doesn't exist.";
                System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
                throw new IOException(msg, e);
            } catch (CsvReadException e) {
                final String msg = "loadPlayers: Csv Parse issue.";
                System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
                throw new IOException(msg, e);
            }
        }
        return "Saved Players successfully loaded.";
    }
    
    public static String loadWeapons() throws IOException {
        try (InputStream file_stream = getContext().getResourceAsStream(Constants.WEAPONS)) {
            InputStreamReader stream_reader = new InputStreamReader(file_stream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(stream_reader);
            String line = null;
            if((line = reader.readLine()) != null) //skip header line
            while((line = reader.readLine()) != null) {
                LoadSave.loaded_weapons.add(Weapon.loadFromCsv(line));
            }
        } catch (IOException e) {
            final String msg = "loadWeapons: Couldn't read or find file.";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
            throw new IOException(msg, e);
        } catch (CsvReadException e) {
            final String msg = "loadWeapons: Csv Parse issue.";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
            throw new IOException(msg, e);
        }
        return "Weapons successfully loaded.";
    }

    public static String loadMonsters() throws IOException {
        try (InputStream file_stream = getContext().getResourceAsStream(Constants.MONSTERS)) {
            InputStreamReader stream_reader = new InputStreamReader(file_stream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(stream_reader);
            String line = null;
            if((line = reader.readLine()) != null) //skip header line
            while((line = reader.readLine()) != null) {
                LoadSave.loaded_monsters.add(Monster.loadFromCsv(line));
            }
        } catch (IOException e) {
            final String msg = "loadMonsters: Couldn't read or find file.";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
            throw new IOException(msg, e);
        } catch (CsvReadException e) {
            final String msg = "loadMonsters: Csv Parse issue.";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
            throw new IOException(msg, e);
        }
        return "Monsters successfully loaded.";
    }

    public static String loadBackgroundImages() {
        List<Path> paths = LoadSave.getResourcePaths(Constants.IMG_GAME_RES);
        for(var path : paths) {
            String filename = path.getFileName().toString();
            Optional<BufferedImage> image = LoadSave.loadImage(Constants.IMG_GAME_RES+filename);
            if(image.isPresent()) {
                LoadSave.loaded_backgrounds.add(image.get());
            }
            else {
                System.out.println("loadBackgroundImages: failed to load");
            }
        }
        return "Background images loaded.";
    }

    public static Optional<BufferedImage> loadImage(String img_res) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getContext().getResourceAsStream(img_res));
        } catch (IOException e) {
            final String msg = "loadImage: Couldn't read or find image.";
            System.out.println(Constants.ANSI_RED + msg + Constants.ANSI_RESET);
        }
        return Optional.ofNullable(img);
    }
}

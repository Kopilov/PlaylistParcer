import java.util.*;
import java.io.*;

public class PlaylistParcer {
    public static void main(String[] args) {
        String playlist = args[0];
        String byOrdernumberDir = args[1];
        String byNameDir = args[2];

        PlaylistParcer parcer = new PlaylistParcer(playlist, byOrdernumberDir, byNameDir);
        try {
            parcer.parcePlaylist();
            parcer.createChannelLinks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String playlist;
    private String byOrdernumberDir;
    private String byNameDir;

    private List<String> channelsByOrdernumber = new ArrayList<String>();

    public PlaylistParcer(String playlist, String byOrdernumberDir, String byNameDir) {
        this.playlist = playlist;
        this.byOrdernumberDir = byOrdernumberDir;
        this.byNameDir = byNameDir;
    }

    private void parcePlaylist() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(playlist));
        String line;
        reader.readLine();// skip #EXTM3U header
        while ((line = reader.readLine()) != null) { // read #EXTINF
            reader.readLine();// skip url
            String[] extinf = line.split(",");
            if (extinf.length > 1) {
                String name = extinf[1];
                channelsByOrdernumber.add(name);
            }
        }
    }

    private void createChannelLinks() throws IOException {
        createNameOrderedChannelLinks();
        createNumberOrderedChannelLinks();
    }

    private void createNameOrderedChannelLinks() throws IOException {
        int i = 0;
        for (String name: channelsByOrdernumber) {
            createChannelLink(byNameDir, name, i, false);
            i++;
        }
    }

    private void createNumberOrderedChannelLinks() throws IOException {
        int i = 0;
        for (String name: channelsByOrdernumber) {
            createChannelLink(byOrdernumberDir, name, i, true);
            i++;
        }
    }

    private void createChannelLink(String directory, String name, int number, boolean numberInName) throws IOException {
        String filename;
        String numberF = String.format("%03d", number);
        if (numberInName) {
            filename = "" + numberF + "-" + name + ".desktop";
        } else {
            filename = name + ".desktop";
        }
        File link = new File(directory + System.getProperty("file.separator") + filename);
        link.createNewFile();
        PrintStream printStream = new PrintStream(new FileOutputStream(link));
        printStream.println("[Desktop Entry]");
        printStream.println("Type=Application");
        if (numberInName) {
            printStream.println("Name=" + numberF + " " + name);
        } else {
            printStream.println("Name=" + name);
        }
        printStream.println("Exec=rkmpv --playlist=" + playlist + " --playlist-start=" + number);
        printStream.println("Terminal=true");
    }
}

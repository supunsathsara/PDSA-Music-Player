/*
 * Group Members:
 * 1. D G Savindu Supun Sathsara : GAHDSE231F-008
 * 2.  = GAHDSE231F-000
 * 3.  = GAHDSE231F-000
 * 
 * PDSA CW PROJECT
 * Harmonizing Melodies
 * 
 * This is a music player that can play a playlist
 * The playlist is implemented using a doubly linked list
 * 
 * The Web Version of this project can be found at: https://supunsathsara.github.io/PDSA-Music-Player
 * Source Code: https://github.com/supunsathsara/PDSA-Music-Player
 * 
 */

 import java.util.Scanner;

/*
 * Song class
 * This class represents a node in our doubly linked list
 */

class Song {
    private String title;
    private String artist;
    private Song next;
    private Song prev;
    
    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
        this.next = null;
        this.prev = null;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getArtist() {
        return this.artist;
    }
    
    public Song getNext() {
        return this.next;
    }
    
    public Song getPrev() {
        return this.prev;
    }
    
    public void setNext(Song next) {
        this.next = next;
    }
    
    public void setPrev(Song prev) {
        this.prev = prev;
    }
    
    public String toString() {
        return this.title + " by " + this.artist;
    }

}

/*
 * Playlist class
 * This class represents a doubly linked list of Song objects
 */
class Playlist {
    private Song head;
    private Song tail;
    private Song cursor;
    
    public Playlist() {
        this.head = null;
        this.tail = null;
        this.cursor = null;
    }
    
    public Song getHead() {
        return this.head;
    }
    
    public Song getTail() {
        return this.tail;
    }
    
    public Song getCursor() {
        return this.cursor;
    }
    
    public void addSong(Song song) {
        if (this.head == null) {
            this.head = song;
            this.tail = song;
            this.cursor = song;
        } else {
            this.tail.setNext(song);
            song.setPrev(this.tail);
            this.tail = song;
        }
    }
    
    public void removeSong(Song song) {
        //remove song from playlist if the title and artist match
        Song current = this.head;
        while (current != null) {
            if (current.getTitle().equals(song.getTitle()) && current.getArtist().equals(song.getArtist())) {
                if (current == this.head) {
                    this.head = current.getNext();
                    if (this.head != null) {
                        this.head.setPrev(null);
                    }
                } else if (current == this.tail) {
                    this.tail = current.getPrev();
                    if (this.tail != null) {
                        this.tail.setNext(null);
                    }
                } else {
                    current.getPrev().setNext(current.getNext());
                    current.getNext().setPrev(current.getPrev());
                }
                if (current == this.cursor) {
                    this.cursor = null;
                }
                return;
            }
            current = current.getNext();
        }
    }
    
    public void removeAllSongs() {
        this.head = null;
        this.tail = null;
        this.cursor = null;
    }

    public void removeFirstSong() {
        if (this.head == null) {
            return;
        } else if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
            this.cursor = null;
        } else {
            this.head = this.head.getNext();
            this.head.setPrev(null);
        }
    }

    public void removeLastSong() {
        if (this.tail == null) {
            return;
        } else if (this.head == this.tail) {
            this.head = null;
            this.tail = null;
            this.cursor = null;
        } else {
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
        }
    }

    public void moveCursorForward() {
        if (this.cursor == null) {
            return;
        } else if (this.cursor == this.tail) {
            this.cursor = this.head;
        } else {
            this.cursor = this.cursor.getNext();
        }
    }
    
    public void moveCursorBackward() {
        if (this.cursor == null) {
            return;
        } else if (this.cursor == this.head) {
            this.cursor = this.tail;
        } else {
            this.cursor = this.cursor.getPrev();
        }
    }
    
    public void moveCursorToHead() {
        this.cursor = this.head;
    }
    
    public void moveCursorToTail() {
        this.cursor = this.tail;
    }
    
    public String toString() {
        String result = "";
        Song current = this.head;
        while (current != null) {
            if (current == this.cursor) {
                result += ">";
            }
            result += current.toString() + "\n";
            current = current.getNext();
        }
        return result;
    }

    public int getSize() {
        int size = 0;
        Song current = this.head;
        while (current != null) {
            size++;
            current = current.getNext();
        }
        return size;
    }

}

/*
 * MusicPlayer class
 * This class represents a music player that can play a playlist
 */
public class MusicPlayer{

    public static void addSong(Playlist pl, Scanner input) {
        System.out.print("Enter song title: ");
        String title = input.nextLine();
        System.out.print("Enter artist: ");
        String artist = input.nextLine();
        Song song = new Song(title, artist);
        pl.addSong(song);

        System.out.println(title + " added to playlist.");
    }

    public static void play(Playlist pl) {
        Song current = pl.getCursor();
        if (current == null) {
            System.out.println("No song to play.");
        } else {
            System.out.println("Playing: " + current.toString());
        }
    }

    public static void playRandom(Playlist pl) {
        // Get the head and tail of the playlist
        Song head = pl.getHead();

        // Check if the playlist is empty
        if (head == null) {
            System.out.println("No song to play.");
            return;
        }

        // Generate a random index within the range of the playlist size
        int playlistSize = pl.getSize();
        int randomIndex = (int) (Math.random() * playlistSize);

        // Traverse the playlist to the randomly selected song
        Song current = head;
        for (int i = 0; i < randomIndex; i++) {
            current = current.getNext();
        }

        // Play the randomly selected song
        System.out.println("Playing: " + current.toString());
    }

   public static void main(String[] args) {

    Playlist pl = new Playlist();
   
    Scanner input = new Scanner(System.in);
    System.out.println("Welcome to the Music Player!");

    int choice = -1;
    while (choice != 0) {
        System.out.println("\n\n");

        System.out.println("1. Add song to playlist");
        System.out.println("2. Remove song from playlist");
        System.out.println("3. Play playlist");
        System.out.println("4. Print playlist");
        System.out.println("5. Play next song");
        System.out.println("6. Play previous song");
        System.out.println("7. Play first song");
        System.out.println("8. Play last song");
        System.out.println("9. Play random song");
        System.out.println("10. Remove all songs");
        System.out.println("11. Remove first song");
        System.out.println("12. Remove last song");
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                addSong(pl, input);
                break;
            case 2:
                System.out.println("Enter song title: ");
                String title = input.nextLine();
                System.out.println("Enter artist: ");
                String artist = input.nextLine();
                Song song = new Song(title, artist);
                pl.removeSong(song);
                break;
            case 3:
                play(pl);
                break;
            case 4:
                System.out.println(pl.toString());
                break;
            case 5:
                pl.moveCursorForward();
                play(pl);
                break;
            case 6:
                pl.moveCursorBackward();
                play(pl);
                break;
            case 7:
                pl.moveCursorToHead();
                play(pl);
                break;
            case 8:
                pl.moveCursorToTail();
                play(pl);
                break;
            case 9:
                playRandom(pl);
                break;
            case 10:
                pl.removeAllSongs();
                break;
            case 11:
                pl.removeFirstSong();
                break;
            case 12:
                pl.removeLastSong();
                break;
            case 0:
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

    }
   } 
}
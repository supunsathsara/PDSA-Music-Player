var audio , playbtn , title , poster , artists , seekslider , seeking=false , seekto, currenttimetext , durationtimetext , playlist_status , dir , playlist , ext , agent , playlists_artists;



playbtn = document.getElementById("playpausebtn");
nextbtn = document.getElementById("nextbtn");
prevbtn = document.getElementById("prevbtn");
seekslider = document.getElementById("seekslider");
currenttimetext = document.getElementById("currenttimetext");
durationtimetext = document.getElementById("durationtimetext");
playlist_status = document.getElementById("playlist_status");
playlists_artists = document.getElementById("playlist_artist");


//! PDSA CODE IS BELOW THIS


//Class Song is a node in a doubly linked list
class Song {
    constructor(title, artist, poster, audioSrc) {
        this.title = title;
        this.artist = artist;
        this.poster = poster;
        this.audioSrc = audioSrc;
        this.prev = null;
        this.next = null;
    }
}

let currentSong = null;


// Class Playlist is a doubly linked list
class Playlist {

    constructor() {
        this.currentSong = null;
    }

addSong(title, artist, poster, audioSrc) {
    const newSong = new Song(title, artist, poster, audioSrc);

    if (currentSong === null) {
        currentSong = newSong;
    } else {
        newSong.prev = currentSong;
        currentSong.next = newSong;
        currentSong = newSong;
    }

    this.updatePlaylist();
}

removeSong() {
    if (currentSong !== null) {
        if (currentSong.prev !== null) {
            currentSong.prev.next = currentSong.next;
        }
        if (currentSong.next !== null) {
            currentSong.next.prev = currentSong.prev;
        }
        currentSong = currentSong.prev;
        this.updatePlaylist();
        updateAudio();
    }
}

prevSong() {
    if (currentSong !== null && currentSong.prev !== null) {
        currentSong = currentSong.prev;
        updateAudio();
    }

}

nextSong() {
    if (currentSong !== null && currentSong.next !== null) {
        currentSong = currentSong.next;
        updateAudio();
    }

}

updatePlaylist() {
    const playlistContainer = document.querySelector(".playlist-container");
    playlistContainer.innerHTML = '';

    let tempSong = currentSong;
    while (tempSong !== null) {
        const playlistItem = document.createElement('div');
        playlistItem.textContent = `${tempSong.title} - ${tempSong.artist}`;
        playlistItem.classList.add('playlist-item'); // Add a class for styling
        playlistContainer.appendChild(playlistItem);
        tempSong = tempSong.prev;
    }
}

}



// BELOW ARE THE FUNCTIONS FOR THE AUDIO PLAYER

function updateAudio() {
    const imageElement = document.getElementById("image");
    const playlistStatus = document.getElementById("playlist_status");
    const playlistArtist = document.getElementById("playlist_artist");

    if (currentSong !== null) {
        imageElement.src = currentSong.poster;
        playlistStatus.innerHTML = currentSong.title;
        playlistArtist.innerHTML = currentSong.artist;
        audio.src = currentSong.audioSrc;
        audio.play();
    }
}

function playPause(){
    if(audio.paused){
        audio.play();
        document.querySelector(".playpause").classList.add("active");
    }else{
        audio.pause();
        document.querySelector(".playpause").classList.remove("active");
    }
}


function seek(event) {
    if (audio.duration === 0) {
        return;
    }

    const seekslider = document.getElementById("seekslider");

    if (seeking) {
        const seekPosition = event.clientX - seekslider.offsetLeft;
        
        // Ensure that seekPosition is within the valid range (0 to 100)
        const normalizedSeekPosition = Math.min(100, Math.max(0, seekPosition));

        const seekto = audio.duration * (normalizedSeekPosition / 100);

        if (isFinite(seekto)) {
            audio.currentTime = seekto;
        }
    }
}
function seektimeupdate() {
    const seekslider = document.getElementById("seekslider");
    const currenttimetext = document.getElementById("currenttimetext");
    const durationtimetext = document.getElementById("durationtimetext");

    if (audio.duration) {
        const nt = audio.currentTime * (100 / audio.duration);
        seekslider.value = nt;

        const curmins = Math.floor(audio.currentTime / 60);
        const cursecs = Math.floor(audio.currentTime - curmins * 60);
        const durmins = Math.floor(audio.duration / 60);
        const dursecs = Math.floor(audio.duration - durmins * 60);

        const formatTime = (mins, secs) => {
            return `${mins < 10 ? "0" : ""}${mins}:${secs < 10 ? "0" : ""}${secs}`;
        };

        currenttimetext.innerHTML = formatTime(curmins, cursecs);
        durationtimetext.innerHTML = formatTime(durmins, dursecs);
    } else {
        currenttimetext.innerHTML = "00:00";
        durationtimetext.innerHTML = "00:00";
    }
}

let checkbox = document.querySelector('input[name=theme]');
checkbox.addEventListener('change',function(){
    if(this.checked){
        document.documentElement.setAttribute('data-theme','dark');
    }else{
        document.documentElement.setAttribute('data-theme','light');
    }
})



//playlist = null;
playlist = new Playlist();

document.addEventListener("DOMContentLoaded", function() {
    
    

    audio = new Audio();
// audio.src = dir+playlist[0]+ext;
// audio.loop = false;


playbtn.addEventListener("click",playPause);
nextbtn.addEventListener("click",playlist.nextSong);
prevbtn.addEventListener("click",playlist.prevSong);
seekslider.addEventListener("mousedown" , function(event){ seeking=true; seek(event);});
seekslider.addEventListener("mousemove",function(event){ seek(event);});

seekslider.addEventListener("mouseup", function(){seeking=false;});

audio.addEventListener("timeupdate",function(){seektimeupdate();});
audio.addEventListener("ended",function(){
    switchTrack();
});


playlist.addSong("Starboy", "The Weeknd & Daft Punk", "images/1.jpg", "songs/Starboy.mp3");
playlist.addSong("I Was Never There", "The Weeknd & Gesaffelstein", "images/2.jpg", "songs/I_Was_Never_There.mp3");
playlist.addSong("LowLife", "Future & The Weeknd", "images/3.jpg", "songs/LowLife.mp3");

console.log(currentSong)

playlist.updatePlaylist();
updateAudio();

});
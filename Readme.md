# m3u8 playlist parcer

Small utility to download m3u8 playlist (such as IPTV) and generate separate Freedesktop entry
file for each item. Each generated entry starts player at the accordinate playlist item (such as IPTV channel).

## Adopted for [Pine64](https://www.pine64.org/) [ROCKPro64](https://wiki.pine64.org/index.php/ROCKPro64_Main_Page) client in the current version
Generated Freedesktop entries have [rkmpv](https://github.com/ayufan-rock64/linux-build/blob/master/recipes/video-playback.md) as the *Exec* argument that is actually
a shell script calling mpv with options specific for RK3399 (ROCKPro64 SOC) video acceleration.

You can customize Freedesktop entries generation as you want.

Make pullrequests for other players support :-)

## How To Install, Repository Structure
Here we have a small Java program, POSIX-shell script and Freedesktop entry files that can be
simply copied to user $HOME/.local directory to be used.  
Also you should install JDK (such as openjdk-11-jdk-headless) for customizing Java code
or JRE (such as openjdk-11-jre-headless) only for executing.

Set PLAYLIST_SOURCE value for your provider at $HOME/.local/share/playlistparcer/settings file.

## IPTV provider branding
I had tested the prototype with [SkyNet](https://www.sknt.ru/) provider. Currently their logo and PLAYLIST_SOURCE
are left inside the project as an example. All rights for the logo belongs to SkyNet company.

The software is not vendor-locked and should be compatible with any non-crypted m3u8 playlist-provided IPTV.

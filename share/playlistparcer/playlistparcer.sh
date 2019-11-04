#!/bin/sh

# resolve links - $0 may be a softlink
PRG="$0"
while [ -h "$PRG" ]; do
    ls="$(ls -ld "$PRG")"
    link="${ls##*-> }" # remove largest prefix: yields link target (behind ->)
    if [ "$link" != "${link#/}" ]; then # remove prefix / if present
        # path was absolute
        PRG="$link"
    else
        # was not
        PRG="$(dirname "$PRG")/$link"
    fi
done

DIR="$(dirname "$PRG")"

. "$DIR/settings"

generate_links() {
    rm -rf "$WORKDIR"
    mkdir "$WORKDIR"
    cp "$DIR/PlaylistParcer.class" "$WORKDIR"
    cd "$WORKDIR"
    curl $PLAYLIST_SOURCE > .playlist.m3u8
    mkdir byOrder
    mkdir byName
    java PlaylistParcer "$(pwd)/.playlist.m3u8" byOrder byName
}

generate_links

if [ "$1" = "original_order" ]; then
    xdg-open byOrder
else
    xdg-open byName
fi

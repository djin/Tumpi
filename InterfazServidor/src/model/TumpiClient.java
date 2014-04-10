package model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import modelos.Cancion;


public class TumpiClient {
	private final String uuid;
	private Set<Integer> votedSongs;

	public TumpiClient(String uuid) {
		this.uuid = uuid;
		votedSongs = new HashSet<Integer>();
	}

	public String getUUID() {
		return uuid;
	}

	public void add(Cancion cancion) {
		final int id = cancion.getId();
		votedSongs.add(id);
	}

	public void add(Integer songId) {
		votedSongs.add(songId);
	}

	public boolean remove(Cancion cancion) {
		final int id = cancion.getId();
		return votedSongs.remove(id);
	}

	public boolean remove(Integer songId) {
		return votedSongs.remove(songId);
	}

	public boolean contains(Cancion cancion) {
		return votedSongs.contains(cancion.getId());
	}

	public boolean contains(Integer songId) {
		return votedSongs.contains(songId);
	}

	public Iterator<Integer> votedSongsIterator() {
		return votedSongs.iterator();
	}

	public String votedSongsAsString() {
		StringBuilder builder = new StringBuilder();
		for (Integer songId : votedSongs) {
			builder.append(songId);
			builder.append(",");
		}
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
}

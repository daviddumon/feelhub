package com.feelhub.patch;

import com.google.common.base.Objects;

public final class Version implements Comparable<Version> {

	public Version(int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return String.valueOf(version);
	}

	@Override
	public int compareTo(final Version autre) {
		if (autre.equals(this)) {
			return 0;
		}
		return version > autre.version ? 1 : -1;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Version)) {
			return false;
		}
		final Version autre = (Version) obj;
		return Objects.equal(version, autre.version);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(version);
	}

	private int version;
}

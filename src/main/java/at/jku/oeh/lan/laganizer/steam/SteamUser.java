package at.jku.oeh.lan.laganizer.steam;

import java.util.Date;

import org.json.JSONObject;

public class SteamUser {
	private long id;
	private short communityVisibilityState;
	private short profileState;
	private String personaName;
	private Date lastLogOff;
	private String profileUrl;
	private String avatarUrl;
	private String avatarMediumUrl;
	private String avatarFullUrl;
	private short personaState;
	private String realName;
	private String primaryClanId;
	private Date timeCreated;
	private short personaStateFlags;
	private String locCountryCode;
	private String locStateCode;
	private long locCityId;
	
	public SteamUser() {
	}
	
	public SteamUser(long id, short communityVisibilityState,
			short profileState, String personaName, Date lastLogOff,
			String profileUrl, String avatarUrl, String avatarMediumUrl,
			String avatarFullUrl, short personaState, String realName,
			String primaryClanId, Date timeCreated, short personaStateFlags,
			String locCountryCode, String locStateCode, long locCityId) {
		super();
		this.id = id;
		this.communityVisibilityState = communityVisibilityState;
		this.profileState = profileState;
		this.personaName = personaName;
		this.lastLogOff = lastLogOff;
		this.profileUrl = profileUrl;
		this.avatarUrl = avatarUrl;
		this.avatarMediumUrl = avatarMediumUrl;
		this.avatarFullUrl = avatarFullUrl;
		this.personaState = personaState;
		this.realName = realName;
		this.primaryClanId = primaryClanId;
		this.timeCreated = timeCreated;
		this.personaStateFlags = personaStateFlags;
		this.locCountryCode = locCountryCode;
		this.locStateCode = locStateCode;
		this.locCityId = locCityId;
	}

	public SteamUser(JSONObject player) {
		id = player.getLong("steamid");
		communityVisibilityState = (short) player.getInt("communityvisibilitystate");
		profileState = (short) player.getInt("profilestate");
		personaName = player.getString("personaname");
		lastLogOff = new Date(player.getLong("lastlogoff"));
		profileUrl = player.getString("profileurl");
		avatarUrl = player.getString("avatar");
		avatarMediumUrl = player.getString("avatarmedium");
		avatarFullUrl = player.getString("avatarfull");
		personaState = (short) player.getInt("personastate");
		realName = player.getString("realname");
		primaryClanId = player.getString("primaryclanid");
		timeCreated = new Date(player.getLong("timecreated"));
		personaStateFlags = (short) player.getInt("personastateflags");
		locCountryCode = player.getString("loccountrycode");
		locStateCode = player.getString("locstatecode");
		locCityId = (short) player.getInt("loccityid");
	}

	@Override
	public String toString() {
		return "SteamUser [id=" + id + ", communityVisibilityState="
				+ communityVisibilityState + ", profileState=" + profileState
				+ ", personaName=" + personaName + ", lastLogOff=" + lastLogOff
				+ ", profileUrl=" + profileUrl + ", avatarUrl=" + avatarUrl
				+ ", avatarMediumUrl=" + avatarMediumUrl + ", avatarFullUrl="
				+ avatarFullUrl + ", personaState=" + personaState
				+ ", realName=" + realName + ", primaryClanId=" + primaryClanId
				+ ", timeCreated=" + timeCreated + ", personaStateFlags="
				+ personaStateFlags + ", locCountryCode=" + locCountryCode
				+ ", locStateCode=" + locStateCode + ", locCityId=" + locCityId
				+ "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public short getCommunityVisibilityState() {
		return communityVisibilityState;
	}

	public void setCommunityVisibilityState(short communityVisibilityState) {
		this.communityVisibilityState = communityVisibilityState;
	}

	public short getProfileState() {
		return profileState;
	}

	public void setProfileState(short profileState) {
		this.profileState = profileState;
	}

	public String getPersonaName() {
		return personaName;
	}

	public void setPersonaName(String personaName) {
		this.personaName = personaName;
	}

	public Date getLastLogOff() {
		return lastLogOff;
	}

	public void setLastLogOff(Date lastLogOff) {
		this.lastLogOff = lastLogOff;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarMediumUrl() {
		return avatarMediumUrl;
	}

	public void setAvatarMediumUrl(String avatarMediumUrl) {
		this.avatarMediumUrl = avatarMediumUrl;
	}

	public String getAvatarFullUrl() {
		return avatarFullUrl;
	}

	public void setAvatarFullUrl(String avatarFullUrl) {
		this.avatarFullUrl = avatarFullUrl;
	}

	public short getPersonaState() {
		return personaState;
	}

	public void setPersonaState(short personaState) {
		this.personaState = personaState;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPrimaryClanId() {
		return primaryClanId;
	}

	public void setPrimaryClanId(String primaryClanId) {
		this.primaryClanId = primaryClanId;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	public short getPersonaStateFlags() {
		return personaStateFlags;
	}

	public void setPersonaStateFlags(short personaStateFlags) {
		this.personaStateFlags = personaStateFlags;
	}

	public String getLocCountryCode() {
		return locCountryCode;
	}

	public void setLocCountryCode(String locCountryCode) {
		this.locCountryCode = locCountryCode;
	}

	public String getLocStateCode() {
		return locStateCode;
	}

	public void setLocStateCode(String locStateCode) {
		this.locStateCode = locStateCode;
	}

	public long getLocCityId() {
		return locCityId;
	}

	public void setLocCityId(long locCityId) {
		this.locCityId = locCityId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avatarFullUrl == null) ? 0 : avatarFullUrl.hashCode());
		result = prime * result
				+ ((avatarMediumUrl == null) ? 0 : avatarMediumUrl.hashCode());
		result = prime * result
				+ ((avatarUrl == null) ? 0 : avatarUrl.hashCode());
		result = prime * result + communityVisibilityState;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastLogOff == null) ? 0 : lastLogOff.hashCode());
		result = prime * result + (int) (locCityId ^ (locCityId >>> 32));
		result = prime * result
				+ ((locCountryCode == null) ? 0 : locCountryCode.hashCode());
		result = prime * result
				+ ((locStateCode == null) ? 0 : locStateCode.hashCode());
		result = prime * result
				+ ((personaName == null) ? 0 : personaName.hashCode());
		result = prime * result + personaState;
		result = prime * result + personaStateFlags;
		result = prime * result
				+ ((primaryClanId == null) ? 0 : primaryClanId.hashCode());
		result = prime * result + profileState;
		result = prime * result
				+ ((profileUrl == null) ? 0 : profileUrl.hashCode());
		result = prime * result
				+ ((realName == null) ? 0 : realName.hashCode());
		result = prime * result
				+ ((timeCreated == null) ? 0 : timeCreated.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SteamUser other = (SteamUser) obj;
		if (avatarFullUrl == null) {
			if (other.avatarFullUrl != null)
				return false;
		} else if (!avatarFullUrl.equals(other.avatarFullUrl))
			return false;
		if (avatarMediumUrl == null) {
			if (other.avatarMediumUrl != null)
				return false;
		} else if (!avatarMediumUrl.equals(other.avatarMediumUrl))
			return false;
		if (avatarUrl == null) {
			if (other.avatarUrl != null)
				return false;
		} else if (!avatarUrl.equals(other.avatarUrl))
			return false;
		if (communityVisibilityState != other.communityVisibilityState)
			return false;
		if (id != other.id)
			return false;
		if (lastLogOff == null) {
			if (other.lastLogOff != null)
				return false;
		} else if (!lastLogOff.equals(other.lastLogOff))
			return false;
		if (locCityId != other.locCityId)
			return false;
		if (locCountryCode == null) {
			if (other.locCountryCode != null)
				return false;
		} else if (!locCountryCode.equals(other.locCountryCode))
			return false;
		if (locStateCode == null) {
			if (other.locStateCode != null)
				return false;
		} else if (!locStateCode.equals(other.locStateCode))
			return false;
		if (personaName == null) {
			if (other.personaName != null)
				return false;
		} else if (!personaName.equals(other.personaName))
			return false;
		if (personaState != other.personaState)
			return false;
		if (personaStateFlags != other.personaStateFlags)
			return false;
		if (primaryClanId == null) {
			if (other.primaryClanId != null)
				return false;
		} else if (!primaryClanId.equals(other.primaryClanId))
			return false;
		if (profileState != other.profileState)
			return false;
		if (profileUrl == null) {
			if (other.profileUrl != null)
				return false;
		} else if (!profileUrl.equals(other.profileUrl))
			return false;
		if (realName == null) {
			if (other.realName != null)
				return false;
		} else if (!realName.equals(other.realName))
			return false;
		if (timeCreated == null) {
			if (other.timeCreated != null)
				return false;
		} else if (!timeCreated.equals(other.timeCreated))
			return false;
		return true;
	}

}

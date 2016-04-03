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
	private Long gameId;
	private String gameExtraInfo;
	private String gameServerIp;
	
	public SteamUser() {
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
		locCityId = player.getLong("loccityid");
		if (player.has("gameid")) {
			gameId = player.getLong("gameid");
		}
		if (player.has("gameextrainfo")) {
			gameExtraInfo = player.getString("gameextrainfo");
		}
		if (player.has("gameserverip")) {
			gameServerIp = player.getString("gameserverip");
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
			return false;
		return true;
	}

	public SteamUser(long id, short communityVisibilityState, short profileState, String personaName, Date lastLogOff,
			String profileUrl, String avatarUrl, String avatarMediumUrl, String avatarFullUrl, short personaState,
			String realName, String primaryClanId, Date timeCreated, short personaStateFlags, String locCountryCode,
			String locStateCode, long locCityId, Long gameId, String gameExtraInfo, String gameServerIp) {
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
		this.gameId = gameId;
		this.gameExtraInfo = gameExtraInfo;
		this.gameServerIp = gameServerIp;
	}

	@Override
	public String toString() {
		return "SteamUser [id=" + id + ", communityVisibilityState=" + communityVisibilityState + ", profileState="
				+ profileState + ", " + (personaName != null ? "personaName=" + personaName + ", " : "")
				+ (lastLogOff != null ? "lastLogOff=" + lastLogOff + ", " : "")
				+ (profileUrl != null ? "profileUrl=" + profileUrl + ", " : "")
				+ (avatarUrl != null ? "avatarUrl=" + avatarUrl + ", " : "")
				+ (avatarMediumUrl != null ? "avatarMediumUrl=" + avatarMediumUrl + ", " : "")
				+ (avatarFullUrl != null ? "avatarFullUrl=" + avatarFullUrl + ", " : "") + "personaState="
				+ personaState + ", " + (realName != null ? "realName=" + realName + ", " : "")
				+ (primaryClanId != null ? "primaryClanId=" + primaryClanId + ", " : "")
				+ (timeCreated != null ? "timeCreated=" + timeCreated + ", " : "") + "personaStateFlags="
				+ personaStateFlags + ", " + (locCountryCode != null ? "locCountryCode=" + locCountryCode + ", " : "")
				+ (locStateCode != null ? "locStateCode=" + locStateCode + ", " : "") + "locCityId=" + locCityId + ", "
				+ (gameId != null ? "gameId=" + gameId + ", " : "")
				+ (gameExtraInfo != null ? "gameExtraInfo=" + gameExtraInfo + ", " : "")
				+ (gameServerIp != null ? "gameServerIp=" + gameServerIp : "") + "]";
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
	
	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public String getGameExtraInfo() {
		return gameExtraInfo;
	}

	public void setGameExtraInfo(String gameExtraInfo) {
		this.gameExtraInfo = gameExtraInfo;
	}

	public String getGameServerIp() {
		return gameServerIp;
	}

	public void setGameServerIp(String gameServerIp) {
		this.gameServerIp = gameServerIp;
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
}

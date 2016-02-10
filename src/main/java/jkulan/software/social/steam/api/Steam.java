package jkulan.software.social.steam.api;

import org.springframework.social.ApiBinding;

public interface Steam extends ApiBinding {
	String getVerifiedSteamId();
}

package at.jku.oeh.lan.laganizer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.oeh.lan.laganizer.dto.RESTDataWrapperDTO;
import at.jku.oeh.lan.laganizer.model.Team;
import at.jku.oeh.lan.laganizer.model.TeamDAO;
import at.jku.oeh.lan.laganizer.model.Tournament;
import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.dao.TournamentDAO;
import at.jku.oeh.lan.laganizer.model.dao.UserDAO;

@RestController
@RequestMapping("/teams/")
public class TeamController {

    @Autowired
    private TeamDAO teamDao;
    @Autowired
    private UserDAO userDao;
    @Autowired
    private TournamentDAO tournamentDao;

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> create(@RequestParam long userId, @RequestParam long tournamentId){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
		result.setSuccess(false);
		
    	Team team = new Team();
    	
    	User user = userDao.findOne(userId);
    	if(user != null){
        	team.setName(user.getName());
        	team.addPlayer(user);
        	
        	Tournament t = tournamentDao.findOne(tournamentId);
        	if(t != null){
        		team.setTournament(t);

            	teamDao.save(team);
            	result.setSuccess(true);
        	}
    	}
    	
    	result.setData(team);
    	return result;
    }
    
    @RequestMapping(value = "addPlayer", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> addPlayer(@RequestParam long id, @RequestParam long userId){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = teamDao.findOne(id);
		result.setSuccess(false);
    	
    	if(team != null){
        	User user;
        	if((user = userDao.findOne(userId)) != null){
            	team.addPlayer(user);
            	teamDao.save(team);
            	result.setSuccess(true);
        	}
    	}

    	result.setData(team);
    	return result;
    }
    
    @RequestMapping(value = "delPlayer", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> delPlayer(@RequestParam long id, @RequestParam long userId){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = teamDao.findOne(id);
    	
    	result.setSuccess(false);
    	
    	if(team != null){
        	User user;
        	if((user = userDao.findOne(userId)) != null){
        		team.delPlayer(user);
            	teamDao.save(team);
            	result.setSuccess(true);
        	}
        	
        	if(team.getPlayers().size() <= 0){
        		teamDao.delete(id);
        	}
    	}

    	result.setData(team);
    	return result;
    }
    
    @RequestMapping(value = "changeName", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> changeName(@RequestParam long id, @RequestParam String name){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = teamDao.findOne(id);
    	
    	if(team == null){
    		result.setSuccess(false);
    	}else{
        	team.setName(name);
        	teamDao.save(team);
        	result.setSuccess(true);
    	}
    	
    	result.setData(team);
    	return result;
    }
    
    @RequestMapping(value = "find", method = RequestMethod.GET)
    public RESTDataWrapperDTO<Team> getTeam(@RequestParam long id){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = teamDao.findOne(id);
    	
		result.setSuccess(team != null);
    	result.setData(team);
    	return result;
    }
}

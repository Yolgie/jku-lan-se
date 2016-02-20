package jkulan.software.controllers;

import jkulan.software.dto.RESTDataWrapperDTO;
import jkulan.software.model.ArrivalAction;
import jkulan.software.model.ArrivalLog;
import jkulan.software.model.ArrivalLogDAO;
import jkulan.software.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/arrivalLog/")
@PermitAll
public class ArrivalLogController {

    @Autowired
    private ArrivalLogDAO arrivalLogDAO;
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "show", method = RequestMethod.GET)
    public RESTDataWrapperDTO list() {
        return new RESTDataWrapperDTO<>((Serializable) arrivalLogDAO.findAll(), true);
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public RESTDataWrapperDTO<ArrivalLog> create(@RequestParam long userId,
                                                 @RequestParam int action,
                                                 @RequestParam long reporterId,
                                                 @RequestParam String note) {
        RESTDataWrapperDTO<ArrivalLog> result = new RESTDataWrapperDTO<>();
        ArrivalLog arrivalLog = new ArrivalLog();
        //todo check if all tose are valid assignments & if users exits, ...
        arrivalLog.setUser(userDAO.findOne(userId));
        arrivalLog.setAction(ArrivalAction.values()[action]);
        arrivalLog.setTimestamp(new Date());
        arrivalLog.setReporter(userDAO.findOne(reporterId));
        arrivalLog.setNote(note);
        arrivalLogDAO.save(arrivalLog);

        result.setData(arrivalLog);
        result.setSuccess(true);
        return result;
    }
}

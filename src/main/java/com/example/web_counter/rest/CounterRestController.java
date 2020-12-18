package com.example.web_counter.rest;

import com.example.web_counter.model.Site;
import com.example.web_counter.model.User;
import com.example.web_counter.model.Visit;
import com.example.web_counter.repository.SiteRepository;
import com.example.web_counter.repository.VisitRepository;
import com.example.web_counter.service.CounterService;
import com.example.web_counter.service.CounterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("api")
public class CounterRestController {

    @Autowired
    CounterService counterService;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private VisitRepository visitRepository;

    @GetMapping("test")
    public String test() {return "Test1";}

    @PostMapping("savevisit")
    public ResponseEntity<Map> saveVisit( @RequestBody(required = true) String username,
                                            @RequestBody(required = true) String url){

        User user = this.counterService.getUserByUsername(username);
        Map<String, String> result = new HashMap<>();

        if (user == null){
            result.put("result", "Error. User not specified");
            return new ResponseEntity<Map>(result,HttpStatus.BAD_REQUEST);
        }

        Site site = this.counterService.getSiteByUrl(url);
        if (site == null){
            Site tempSite = new Site();
            tempSite.setUrl(url);

            siteRepository.save(tempSite);

            site = this.counterService.getSiteByUrl(url);
        }

        this.counterService.saveVisit(user.getId(), site.getId());

        result.put("result", "Success. Added new record");
        return new ResponseEntity<Map>(result,HttpStatus.OK);
    }

    //@RequestMapping(name = "stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping("stats")
    public ResponseEntity<Map> getStatistics(   @RequestParam(required = true) String dateFrom,
                                                @RequestParam(required = true) String dateTo,
                                                @RequestParam(required = false) String username) throws Exception{

        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
        Map<String, Integer> result = new HashMap<>();

        String time_stamp_regex = "[0-3]{1}[0-9]{1}[0-1]{1}[0-9]{1}[0-9]{4}[0-2]{1}[0-9]{1}[0-5]{1}[0-9]{1}[0-5]{1}[0-9]{1}";
        String time_stamp_regex_short = "[0-3]{1}[0-9]{1}[0-1]{1}[0-9]{1}[0-9]{4}";

        if (!Pattern.matches(time_stamp_regex, dateFrom)){
            if (Pattern.matches(time_stamp_regex_short, dateFrom)){
                dateFrom = dateFrom + "000000";
            }
            else {
                result.put("error", 1);
                return new ResponseEntity<Map>(result, HttpStatus.BAD_REQUEST);
            }
        }

        Date d_date_from = formatter.parse(dateFrom);

        if (!Pattern.matches(time_stamp_regex, dateTo)){
            if (Pattern.matches(time_stamp_regex_short, dateTo)){
                dateFrom = dateFrom + "000000";
            }
            else {
                result.put("error", 1);
                return new ResponseEntity<Map>(result, HttpStatus.BAD_REQUEST);
            }
        }

        Date d_date_to = formatter.parse(dateTo);

        if (username == null){
            result = this.counterService.getStatistics(d_date_from, d_date_to);
        }
        else {
            result = this.counterService.getStatistics(d_date_from, d_date_to, 1);
        }

        return new ResponseEntity<Map>(result, HttpStatus.OK);
    }

}

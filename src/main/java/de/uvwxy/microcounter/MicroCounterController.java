package de.uvwxy.microcounter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MicroCounterController {
    private static long startup = System.currentTimeMillis();
    private static long counter = 0;

    @RequestMapping(value = "/", produces = { "text/html" }, consumes = { "*/*" }, method = RequestMethod.GET)
    public ResponseEntity<String> getIndex() {
        counter++;

        long uptime = System.currentTimeMillis() - startup;
        long s = (uptime / 1000) % 60;
        long m = (uptime / 1000 / 60) % 60;
        long h = (uptime / 1000 / 60 / 60) % 60;
        long d = (uptime / 1000 / 60 / 60 / 24);

        String result = "";
        result += "<html><body>";
        result += "<br>Hits: <b>" + counter + "</b>";
        result += String.format("<br>Uptime: <b>%d d %d h %d m %d s</b>", d, h, m, s);
        result += "<br>More cool projects: <a href=\"https://p3dt.net\" target=\"blank\">p3dt.net</a>";
        result += "<br>Fork this: <a href=\"https://github.com/pauls-3d-things/micro-counter\" target=\"blank\">github.com/pauls-3d-things/micro-counter</a>";
        result += "</body> </html>";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/counter/value", produces = { "application/json" }, consumes = {
            "*/*" }, method = RequestMethod.GET)
    public ResponseEntity<Long> getCounterValue() {
        return new ResponseEntity<>(new Long(counter), HttpStatus.OK);
    }

    @RequestMapping(value = "/counter/inrement", produces = { "application/json" }, consumes = {
            "*/*" }, method = RequestMethod.GET)
    public ResponseEntity<Long> getCounterIncrement() {
        counter++;

        return new ResponseEntity<>(new Long(counter), HttpStatus.OK);
    }

    @RequestMapping(value = "/uptime", produces = { "application/json" }, consumes = {
            "*/*" }, method = RequestMethod.GET)
    public ResponseEntity<Long> getUptime() {
        return new ResponseEntity<>(new Long(System.currentTimeMillis() - startup), HttpStatus.OK);
    }

}

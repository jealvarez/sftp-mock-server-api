package org.jealvarez.sftpmockserver.web.resources;

import org.jealvarez.sftpmockserver.service.SftpService;
import org.jealvarez.sftpmockserver.web.model.Sftp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/sftp")
public class SftpController {

    private final SftpService sftpService;

    public SftpController(final SftpService sftpService) {
        this.sftpService = sftpService;
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/start")
    public ResponseEntity start(@RequestBody final Sftp sftp) {
        try {
            sftpService.start(sftp);
            return new ResponseEntity<>("{ \"message\": \"OK\" }", OK);
        } catch (final Exception exception) {
            return new ResponseEntity<>("{ \"message\": \"" + exception.getCause() + "\" }", INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/stop")
    public ResponseEntity stop() {
        try {
            sftpService.stop();
            return new ResponseEntity<>("{ \"message\": \"OK\" }", OK);
        } catch (final Exception exception) {
            return new ResponseEntity<>("{ \"message\": \"" + exception.getCause() + "\" }", INTERNAL_SERVER_ERROR);
        }
    }

}

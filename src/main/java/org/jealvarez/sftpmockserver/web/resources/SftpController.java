package org.jealvarez.sftpmockserver.web.resources;

import io.swagger.annotations.ApiOperation;
import org.jealvarez.sftpmockserver.service.SftpService;
import org.jealvarez.sftpmockserver.web.factories.ApiResponseFactory;
import org.jealvarez.sftpmockserver.web.model.ApiResponse;
import org.jealvarez.sftpmockserver.web.model.Sftp;
import org.jealvarez.sftpmockserver.web.model.SftpDirectory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sftp")
public class SftpController {

    private final SftpService sftpService;
    private final ApiResponseFactory apiResponseFactory;

    public SftpController(final SftpService sftpService, final ApiResponseFactory apiResponseFactory) {
        this.sftpService = sftpService;
        this.apiResponseFactory = apiResponseFactory;
    }

    @PostMapping("/start")
    @ApiOperation("Starts SFTP server")
    public ApiResponse start(@RequestBody final Sftp sftp) throws Exception {
        sftpService.start(sftp);
        return apiResponseFactory.buildOkResponse();
    }

    @PostMapping("/stop")
    @ApiOperation("Stops SFTP server")
    public ApiResponse stop() throws Exception {
        sftpService.stop();
        return apiResponseFactory.buildOkResponse();
    }

    @GetMapping("/status")
    @ApiOperation("Get SFTP server status")
    public ApiResponse status() {
        final boolean started = sftpService.isStarted();
        return apiResponseFactory.buildOkResponse("{ \"message\": " + started + " }");
    }

    @GetMapping("/directories")
    @ApiOperation("Get remote SFTP server directories")
    public ApiResponse getRemoteDirectories() throws Exception {
        return apiResponseFactory.buildOkResponse(new SftpDirectory(sftpService.getRemoteDirectories()));
    }

}

package org.jealvarez.sftpmockserver.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SftpDirectory {

    private final List<String> directories;

}

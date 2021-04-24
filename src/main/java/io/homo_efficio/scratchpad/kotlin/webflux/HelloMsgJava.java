package io.homo_efficio.scratchpad.kotlin.webflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * @author homo.efficio@gmail.com
 * created on 2021-04-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloMsgJava {

    private String id;
    @Size(max = 3) private String username;
    private String msg;
}

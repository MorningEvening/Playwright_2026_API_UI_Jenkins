package com.api.data;/*
 * @Project PlayWrightUIAutomation
 * @author  pritipradhan
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UsersLombok {

    private String id;
    private String name;
    private String email;
    private String gender;
    private String status;
}

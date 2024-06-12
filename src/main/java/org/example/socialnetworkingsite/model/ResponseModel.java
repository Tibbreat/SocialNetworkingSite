package org.example.socialnetworkingsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private int responseCode;
    private String msg;
    private Object obj;

    public ResponseModel(int responseCode, String msg) {
        this.responseCode = responseCode;
        this.msg = msg;
    }
}

package com.ciphershare.file.dto;

import lombok.Data;

@Data
public class BlockchainRecordDTO {
    private String fileID;
    private String txnHash;
    private String actionType;
}

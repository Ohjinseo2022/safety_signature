package com.safety_signature.safety_signature_back.app.user.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.safety_signature.safety_signature_back.utils.common.Base64StringDeserializer;
import com.safety_signature.safety_signature_back.utils.common.Base64StringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostJoinBody {

    private String id;
    @NotNull
    private String name;
    @NotNull
    private String userId;
    @NotNull
    private String mobile;
    @NotNull
    @JsonDeserialize(using = Base64StringDeserializer.class)
    @JsonSerialize(using = Base64StringSerializer.class)
    private String password;
    @NotNull
    private String image;
}

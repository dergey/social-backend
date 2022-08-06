package com.sergey.zhuravlev.social.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDto {
    @Schema(example = "Y4vNen2p1rWYTUATSpmy.IbS3yBPvs0N1ssZZ2EDwaG5m7qlC1YXruNIjK1Td4MYbCRHbPnmrmvukApatg2vpt7MgFDfuU51074Erfxb.OlRFugWrqn8TeGn0H4OIl2J6FxuZCIrgASy92WqLMLLeLYkyy1mN8382E8IldcqDoEOBoWOMisQ0J0DKkEo8h5")
    private String jwtToken;
}

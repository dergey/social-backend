package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.*;
import com.sergey.zhuravlev.social.entity.Profile;
import com.sergey.zhuravlev.social.enums.AddressType;
import com.sergey.zhuravlev.social.enums.ErrorCode;
import com.sergey.zhuravlev.social.enums.ProfileAttitude;
import com.sergey.zhuravlev.social.enums.RelationshipStatus;
import com.sergey.zhuravlev.social.exception.SocialServiceException;
import com.sergey.zhuravlev.social.mapper.ProfileMapper;
import com.sergey.zhuravlev.social.service.ProfileAttitudeService;
import com.sergey.zhuravlev.social.service.ProfilePropertyService;
import com.sergey.zhuravlev.social.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Profile update endpoints")
@RequestMapping("/api/profile/property")
@RestController
@RequiredArgsConstructor
public class ProfilePropertiesController {

    private final ProfileService profileService;
    private final ProfilePropertyService profilePropertyService;
    private final ProfileAttitudeService profileAttitudeService;

    private final ProfileMapper profileMapper;

    @PutMapping("/submission")
    public ProfileDto updateSubmission(@Valid @RequestBody UpdatePropertyDto<ProfileSubmissionDto> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        currentProfile = profilePropertyService.updateProfileSubmission(currentProfile,
            request.getValue().getFirstName(),
            request.getValue().getMiddleName(),
            request.getValue().getSecondName(),
            request.getValue().getGender());
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

    @PutMapping("/username")
    public ProfileDto updateUsername(@Valid @RequestBody UpdatePropertyDto<String> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        currentProfile = profilePropertyService.updateProfileUsername(currentProfile, request.getValue());
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

    @PutMapping("/birthDate")
    public ProfileDto updateBirthDate(@Valid @RequestBody UpdatePrivatePropertyDto<LocalDate> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        currentProfile = profilePropertyService.updateProfileBirthDate(currentProfile, request.getValue());
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

    @PutMapping("/overview")
    public ProfileDto updateOverview(@Valid @RequestBody UpdatePropertyDto<String> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        currentProfile = profilePropertyService.updateProfileOverview(currentProfile, request.getValue());
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

    @PutMapping("/relationshipStatus")
    public ProfileDto updateRelationshipStatus(@Valid @RequestBody UpdatePropertyDto<RelationshipStatus> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        currentProfile = profilePropertyService.updateProfileRelationshipStatus(currentProfile, request.getValue());
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

    @PutMapping("/workplace")
    public ProfileDto updateWorkplace(@Valid @RequestBody UpdatePrivatePropertyDto<String> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        currentProfile = profilePropertyService.updateProfileWorkplace(currentProfile, request.getValue());
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

    @PutMapping("/citizenship")
    public ProfileDto updateCitizenship(@Valid @RequestBody UpdatePrivatePropertyDto<String> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        currentProfile = profilePropertyService.updateProfileCitizenship(currentProfile, request.getValue());
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

    @PutMapping("/education")
    public ProfileDto updateEducation(@Valid @RequestBody UpdatePrivatePropertyDto<EducationDto> request) {
        // TODO: IMPLEMENT!
        throw new SocialServiceException(ErrorCode.NOT_IMPLEMENTED);
    }

    @PutMapping("/addresses")
    public ProfileDto updateAddresses(@Valid @RequestBody @Size(max = 2) List<UpdatePrivatePropertyDto<UpdateAddressDto>> request) {
        Profile currentProfile = profileService.getCurrentProfile();
        Map<AddressType, UpdateAddressDto> addressMap = request.stream().collect(Collectors.toMap(
            a -> a.getValue().getType(),
            UpdatePrivatePropertyDto::getValue));
        UpdateAddressDto registrationAddress = addressMap.get(AddressType.REGISTRATION_ADDRESS);
        UpdateAddressDto residenceAddress = addressMap.get(AddressType.RESIDENCE_ADDRESS);
        if (registrationAddress != null) {
            currentProfile = profilePropertyService.updateRegistrationAddress(currentProfile,
                registrationAddress.getFirstLine(),
                registrationAddress.getSecondLine(),
                registrationAddress.getCity(),
                registrationAddress.getCountry(),
                registrationAddress.getZipCode());
        }
        if (residenceAddress != null) {
            currentProfile = profilePropertyService.updateResidenceAddress(currentProfile,
                residenceAddress.getFirstLine(),
                residenceAddress.getSecondLine(),
                residenceAddress.getCity(),
                residenceAddress.getCountry(),
                residenceAddress.getZipCode());
        }
        profileAttitudeService.setAttitudeForce(currentProfile, ProfileAttitude.YOU);
        return profileMapper.profileToProfileDto(currentProfile);
    }

}

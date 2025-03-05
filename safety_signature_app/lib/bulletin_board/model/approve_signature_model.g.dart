// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'approve_signature_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

ApproveSignatureRequestModel _$ApproveSignatureRequestModelFromJson(
        Map<String, dynamic> json) =>
    ApproveSignatureRequestModel(
      bulletinBoardId: json['bulletinBoardId'] as String,
    );

Map<String, dynamic> _$ApproveSignatureRequestModelToJson(
        ApproveSignatureRequestModel instance) =>
    <String, dynamic>{
      'bulletinBoardId': instance.bulletinBoardId,
    };

ApproveSignatureMessageModel _$ApproveSignatureMessageModelFromJson(
        Map<String, dynamic> json) =>
    ApproveSignatureMessageModel(
      message: json['message'] as String,
      httpStatus: (json['httpStatus'] as num?)?.toInt(),
    );

Map<String, dynamic> _$ApproveSignatureMessageModelToJson(
        ApproveSignatureMessageModel instance) =>
    <String, dynamic>{
      'message': instance.message,
      'httpStatus': instance.httpStatus,
    };

ApproveSignatureErrorMessageModel _$ApproveSignatureErrorMessageModelFromJson(
        Map<String, dynamic> json) =>
    ApproveSignatureErrorMessageModel(
      message: json['message'] as String,
      httpStatus: (json['httpStatus'] as num?)?.toInt(),
    );

Map<String, dynamic> _$ApproveSignatureErrorMessageModelToJson(
        ApproveSignatureErrorMessageModel instance) =>
    <String, dynamic>{
      'message': instance.message,
      'httpStatus': instance.httpStatus,
    };

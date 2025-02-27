// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'attach_doc_master_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

AttachDocMasterModel _$AttachDocMasterModelFromJson(
        Map<String, dynamic> json) =>
    AttachDocMasterModel(
      id: json['id'] as String,
      attachDocOwnerId: json['attachDocOwnerId'] as String,
      attachDocName: json['attachDocName'] as String,
      attachFileType: json['attachFileType'] as String?,
      attachDocExplain: json['attachDocExplain'] as String?,
      attachDocId: json['attachDocId'] as String,
      attachDocPosition: json['attachDocPosition'] as String,
      attachDocOwnerClassCode: json['attachDocOwnerClassCode'] as String,
      attachDocSize: (json['attachDocSize'] as num).toDouble(),
    );

Map<String, dynamic> _$AttachDocMasterModelToJson(
        AttachDocMasterModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'attachDocOwnerId': instance.attachDocOwnerId,
      'attachDocName': instance.attachDocName,
      'attachFileType': instance.attachFileType,
      'attachDocExplain': instance.attachDocExplain,
      'attachDocId': instance.attachDocId,
      'attachDocPosition': instance.attachDocPosition,
      'attachDocOwnerClassCode': instance.attachDocOwnerClassCode,
      'attachDocSize': instance.attachDocSize,
    };

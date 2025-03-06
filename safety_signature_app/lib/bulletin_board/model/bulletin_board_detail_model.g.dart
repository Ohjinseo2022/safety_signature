// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'bulletin_board_detail_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BulletinBoardDetailModel _$BulletinBoardDetailModelFromJson(
        Map<String, dynamic> json) =>
    BulletinBoardDetailModel(
      id: json['id'] as String,
      attachYn: json['attachYn'] as bool,
      boardContents: json['boardContents'] as String,
      boardTitle: json['boardTitle'] as String,
      createdBy: json['createdBy'] as String,
      completionYn: json['completionYn'] as bool,
      createdDate: json['createdDate'] as String,
      createdDateFormat: json['createdDateFormat'] as String,
      lastModifiedBy: json['lastModifiedBy'] as String,
      lastModifiedDate: json['lastModifiedDate'] as String,
      userMasterDTO:
          UserMinModel.fromJson(json['userMasterDTO'] as Map<String, dynamic>),
      userMasterId: json['userMasterId'] as String,
      signatureCount: (json['signatureCount'] as num).toInt(),
      attachDocList: (json['attachDocList'] as List<dynamic>)
          .map((e) => AttachDocMasterModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      siteAddress: json['siteAddress'] as String?,
    );

Map<String, dynamic> _$BulletinBoardDetailModelToJson(
        BulletinBoardDetailModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'attachYn': instance.attachYn,
      'boardContents': instance.boardContents,
      'boardTitle': instance.boardTitle,
      'completionYn': instance.completionYn,
      'createdBy': instance.createdBy,
      'createdDate': instance.createdDate,
      'createdDateFormat': instance.createdDateFormat,
      'lastModifiedBy': instance.lastModifiedBy,
      'lastModifiedDate': instance.lastModifiedDate,
      'userMasterDTO': instance.userMasterDTO,
      'userMasterId': instance.userMasterId,
      'signatureCount': instance.signatureCount,
      'attachDocList': instance.attachDocList,
      'siteAddress': instance.siteAddress,
    };

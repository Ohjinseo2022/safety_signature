// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'bulletin_board_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BulletinBoardModel _$BulletinBoardModelFromJson(Map<String, dynamic> json) =>
    BulletinBoardModel(
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
    );

Map<String, dynamic> _$BulletinBoardModelToJson(BulletinBoardModel instance) =>
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
    };

import 'package:json_annotation/json_annotation.dart';
import 'package:safety_signature_app/common/model/model_with_id.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
part 'bulletin_board_model.g.dart';

@JsonSerializable()
class BulletinBoardModel implements IModelWithId {
  @override
  final String id;
  final bool attachYn;
  final String boardContents;
  final String boardTitle;
  final bool completionYn;
  final String createdBy;
  final String createdDate;
  final String createdDateFormat;
  final String lastModifiedBy;
  final String lastModifiedDate;
  final UserMinModel userMasterDTO;
  final String userMasterId;
  final int signatureCount;
  BulletinBoardModel({
    required this.id,
    required this.attachYn,
    required this.boardContents,
    required this.boardTitle,
    required this.createdBy,
    required this.completionYn,
    required this.createdDate,
    required this.createdDateFormat,
    required this.lastModifiedBy,
    required this.lastModifiedDate,
    required this.userMasterDTO,
    required this.userMasterId,
    required this.signatureCount,
  });

  factory BulletinBoardModel.fromJson(Map<String, dynamic> json) =>
      _$BulletinBoardModelFromJson(json);

  Map<String, dynamic> toJson() => _$BulletinBoardModelToJson(this);
}

// attachDocList: AttachDocMasterType[] -> 상세에서 조회됨
// interface AttachDocMasterType {
// id: string
// attachDocOwnerId: string
// attachDocName: string
// attachFileType: any
// attachDocExplain: string
// attachDocId: string
// attachDocPosition: string
// attachDocOwnerClassCode: string
// classValue: any
// attachDocReader: any
// attachDocReaderXlsx: any
// attachDocPositionFilename: any
// attachDocSize: number
// attachDocPartitionDTO: any
// attachDownloadLimitDtime: any
// attachStorageLimitDtime: any
// minioDeleteYn: any
// }

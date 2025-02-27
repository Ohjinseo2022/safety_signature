import 'package:json_annotation/json_annotation.dart';
import 'package:safety_signature_app/common/model/model_with_id.dart';
part 'attach_doc_master_model.g.dart';

@JsonSerializable()
class AttachDocMasterModel implements IModelWithId {
  @override
  final String id; //첨부 문서 id
  final String attachDocOwnerId;
  final String attachDocName; //첨부 문서 이름
  final String? attachFileType; //첨부 문서 유형
  final String? attachDocExplain; //첨부 문서 설명
  final String attachDocId;
  final String attachDocPosition; //첨부문서위치
  final String attachDocOwnerClassCode;
  final double attachDocSize;
  AttachDocMasterModel({
    required this.id,
    required this.attachDocOwnerId,
    required this.attachDocName,
    this.attachFileType,
    this.attachDocExplain,
    required this.attachDocId,
    required this.attachDocPosition,
    required this.attachDocOwnerClassCode,
    required this.attachDocSize,
  });

  factory AttachDocMasterModel.fromJson(Map<String, dynamic> json) =>
      _$AttachDocMasterModelFromJson(json);
  Map<String, dynamic> toJson() => _$AttachDocMasterModelToJson(this);
}

// attachDocPartitionDTO: any
// attachDownloadLimitDtime: any
// attachStorageLimitDtime: any
// minioDeleteYn: any

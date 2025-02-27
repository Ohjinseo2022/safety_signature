import 'package:json_annotation/json_annotation.dart';
import 'package:safety_signature_app/bulletin_board/model/bulletin_board_model.dart';
import 'package:safety_signature_app/common/model/attach_doc_master_model.dart';

import '../../user/model/user_model.dart';

part 'bulletin_board_detail_model.g.dart';

@JsonSerializable()
class BulletinBoardDetailModel extends BulletinBoardModel {
  final List<AttachDocMasterModel> attachDocList;
  BulletinBoardDetailModel(
      {required super.id,
      required super.attachYn,
      required super.boardContents,
      required super.boardTitle,
      required super.createdBy,
      super.completionYn,
      required super.createdDate,
      required super.createdDateFormat,
      required super.lastModifiedBy,
      required super.lastModifiedDate,
      required super.userMasterDTO,
      required super.userMasterId,
      required super.signatureCount,
      required this.attachDocList});

  factory BulletinBoardDetailModel.fromJson(Map<String, dynamic> json) =>
      _$BulletinBoardDetailModelFromJson(json);
  Map<String, dynamic> toJson() => _$BulletinBoardDetailModelToJson(this);
}

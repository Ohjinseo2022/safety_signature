import 'package:json_annotation/json_annotation.dart';

part 'approve_signature_model.g.dart';

class ApproveSignatureModelBase {}

class ApproveSignatureLoading extends ApproveSignatureModelBase {}

@JsonSerializable()
class ApproveSignatureRequestModel {
  final String bulletinBoardId;
  final String? constructionBusiness;
  final String? companyName;
  ApproveSignatureRequestModel(
      {required this.bulletinBoardId,
      this.constructionBusiness,
      this.companyName});

  factory ApproveSignatureRequestModel.fromJson(Map<String, dynamic> json) =>
      _$ApproveSignatureRequestModelFromJson(json);

  Map<String, dynamic> toJson() => _$ApproveSignatureRequestModelToJson(this);
}

@JsonSerializable()
class ApproveSignatureMessageModel extends ApproveSignatureModelBase {
  final String message;
  final int? httpStatus;

  ApproveSignatureMessageModel({required this.message, this.httpStatus});

  factory ApproveSignatureMessageModel.fromJson(Map<String, dynamic> json) =>
      _$ApproveSignatureMessageModelFromJson(json);
  Map<String, dynamic> toJson() => _$ApproveSignatureMessageModelToJson(this);
}

@JsonSerializable()
class ApproveSignatureErrorMessageModel extends ApproveSignatureModelBase {
  final String message;
  final int? httpStatus;

  ApproveSignatureErrorMessageModel({required this.message, this.httpStatus});

  factory ApproveSignatureErrorMessageModel.fromJson(
          Map<String, dynamic> json) =>
      _$ApproveSignatureErrorMessageModelFromJson(json);
  Map<String, dynamic> toJson() =>
      _$ApproveSignatureErrorMessageModelToJson(this);
}

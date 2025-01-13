import 'dart:typed_data';

import 'package:json_annotation/json_annotation.dart';

import '../../common/utils/data_utils.dart';

part "post_join_body.g.dart";

@JsonSerializable()
class PostJoinBody {
  final String? id;
  final String name;
  final String userId;
  final String mobile;
  @JsonKey(toJson: DataUtils.plainToBase64)
  final String password;
  @JsonKey(
    fromJson: DataUtils.stringToUint8List,
    toJson: DataUtils.uint8ListToString,
  )
  final Uint8List image;
  PostJoinBody({
    this.id,
    required this.name,
    required this.userId,
    required this.mobile,
    required this.password,
    required this.image,
  });
  factory PostJoinBody.fromJson(Map<String, dynamic> json) =>
      _$PostJoinBodyFromJson(json);

  Map<String, dynamic> toJson() => _$PostJoinBodyToJson(this);
}

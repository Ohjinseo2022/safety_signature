// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'post_join_body.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

PostJoinBody _$PostJoinBodyFromJson(Map<String, dynamic> json) => PostJoinBody(
      id: json['id'] as String?,
      name: json['name'] as String,
      userId: json['userId'] as String,
      mobile: json['mobile'] as String,
      password: json['password'] as String,
      image: DataUtils.stringToUint8List(json['image'] as String),
    );

Map<String, dynamic> _$PostJoinBodyToJson(PostJoinBody instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'userId': instance.userId,
      'mobile': instance.mobile,
      'password': instance.password,
      'image': DataUtils.uint8ListToString(instance.image),
    };

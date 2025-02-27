// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'common_detail_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CommonDetailModel<T> _$CommonDetailModelFromJson<T>(
  Map<String, dynamic> json,
  T Function(Object? json) fromJsonT,
) =>
    CommonDetailModel<T>(
      data: fromJsonT(json['data']),
    );

Map<String, dynamic> _$CommonDetailModelToJson<T>(
  CommonDetailModel<T> instance,
  Object? Function(T value) toJsonT,
) =>
    <String, dynamic>{
      'data': toJsonT(instance.data),
    };

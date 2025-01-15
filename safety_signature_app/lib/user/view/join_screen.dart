import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart' hide Uint8List;
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:safety_signature_app/common/components/common_dialog.dart';
import 'package:safety_signature_app/common/components/custom_text_form_field.dart';
import 'package:safety_signature_app/common/components/pagination_list_view.dart';
import 'package:safety_signature_app/common/const/color.dart';
import 'package:safety_signature_app/common/enumeration/user_status_code.dart';
import 'package:safety_signature_app/common/layout/default_layout.dart';
import 'package:safety_signature_app/common/model/login_response.dart';
import 'package:safety_signature_app/common/provider/go_router.dart';
import 'package:safety_signature_app/common/provider/modal_controller_porivder.dart';
import 'package:safety_signature_app/common/utils/data_utils.dart';
import 'package:safety_signature_app/common/view/root_tab.dart';
import 'package:safety_signature_app/user/components/signature_dialog.dart';
import 'package:safety_signature_app/user/model/post_join_body.dart';
import 'package:safety_signature_app/user/model/user_model.dart';
import 'package:safety_signature_app/user/provider/user_auth_provider.dart';
import 'package:go_router/go_router.dart';
import 'package:syncfusion_flutter_signaturepad/signaturepad.dart';

class JoinScreen extends ConsumerStatefulWidget {
  static String get routeName => "JoinScreen";
  const JoinScreen({super.key});

  @override
  ConsumerState<JoinScreen> createState() => _JoinScreenState();
}

class _JoinScreenState extends ConsumerState<JoinScreen> {
  String? name;
  String? userId;
  String? mobile;
  String password = "";
  String passwordCheck = "";

  String? userIdValid = "";
  String? mobileValid = "";
  String? passwordValid = "";
  String? passwordCheckValid = "";
  bool isSubmit = false;
  bool isSignature = false;
  Uint8List? image;
  @override
  Widget build(BuildContext context) {
    final state = ref.watch(userAuthProvider);
    final isPopUp = ref.watch(modalControllerProvider);
    if (state is UserMinModel) {
      print("설마 여러번 동작하니 ?");
      setState(() {
        name = name ?? state.name;
        userId = userId ?? state.email;
        mobile = mobile ?? state.mobile;
        userIdValid = null;
        mobileValid = null;
      });
    }
    if (isPopUp && (ModalRoute.of(context)?.isCurrent ?? false)) {
      WidgetsBinding.instance.addPostFrameCallback((_) {
        if (state is UserModelError) {
          ref.read(modalControllerProvider.notifier).isPopUp(visibility: false);
          commonDialog(
            context: context,
            title: '회원가입 실패',
            content: Text(
              state.message,
              style: defaultTextStyle,
            ),
            onConfirm: () {},
          );
        }
        if (state is UserMinModel &&
            UserStatusCode.getByCode(state.userStatusCode) ==
                UserStatusCode.ACTIVE) {
          ref.read(modalControllerProvider.notifier).isPopUp(visibility: false);
          commonDialog(
            context: context,
            title: '회원가입 완료',
            content: Text(
              "회원가입이 완료 됐습니다.",
              style: defaultTextStyle,
            ),
            onConfirm: () {
              context.goNamed(RootTab.routeName);
            },
          );
        }
      });
    }
    return DefaultLayout(
      title: "회원가입",
      backgroundColor: SECONDARY_COLOR,
      topAppBarBtn: false,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          _inputField(
            inputValue: name,
            title: "이름",
            placeholder: "이름을 입력해 주세요.",
            onChangedValue: (value) {
              name = value;
            },
            enabled: state is! UserMinModel,
          ),
          _inputField(
            inputValue: userId,
            title: "아이디(이메일)",
            placeholder: "아이디(이메일)을 입력해 주세요. ex)xxx@gmail.com",
            onChangedValue: (value) {
              // setState(() {
              userId = value;
              // });
            },
            validator: (value) {
              userIdValid = DataUtils.emailRegex(value);
              return userIdValid;
            },
            enabled: state is! UserMinModel,
          ),
          _inputField(
              inputValue: mobile,
              title: "핸드폰 번호",
              placeholder: "핸드폰 번호를 입력해 주세요. 숫자만 입력 가능 합니다.",
              onChangedValue: (value) {
                // setState(() {
                mobile = value;
                // });
              },
              inputFormatters: [
                FilteringTextInputFormatter.digitsOnly,
                LengthLimitingTextInputFormatter(11),
              ],
              validator: (value) {
                mobileValid = DataUtils.phoneRegex(value);
                return mobileValid;
              },
              //state 가 UserMinModel 이 아니거나 UserMinModel 이어도 mobile 정보가 없다면
              enabled: state is! UserMinModel ||
                  !(state is UserMinModel && state.mobile != null)),
          _inputField(
            inputValue: password,
            title: "비밀번호",
            placeholder: "비밀번호를 입력해 주세요.",
            onChangedValue: (value) {
              password = value;
              setState(() {
                if (value == "") {
                  passwordCheck = "";
                }
              });
            },
            validator: (value) {
              passwordValid = DataUtils.passwordRegex(value);
              return passwordValid;
            },
            obscureText: true,
          ),
          if (password != "")
            _inputField(
              inputValue: passwordCheck,
              title: "비밀번호 확인",
              placeholder: "비밀번호를 다시한번 입력해주세요.",
              onChangedValue: (value) {
                // setState(() {
                passwordCheck = value;
                // });
              },
              validator: (value) {
                passwordCheckValid = DataUtils.passwordRegex(value) ??
                    (password == passwordCheck ? null : "비밀번호 와 일치하지 않습니다.");
                return passwordCheckValid;
              },
              obscureText: true,
            ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 20.0),
            child: ElevatedButton(
                style: ElevatedButton.styleFrom(),
                onPressed: () async {
                  if (name == "" ||
                      userIdValid != null ||
                      mobileValid != null ||
                      passwordValid != null ||
                      passwordCheckValid != null) {
                    String alertMessage = name == ""
                        ? "이름을 입력해 주세요."
                        : userIdValid.runtimeType == String
                            ? "아이디(이메일)을 확인해 주세요."
                            : mobileValid.runtimeType == String
                                ? "핸드폰 번호를 확인해 주세요 "
                                : passwordValid.runtimeType == String
                                    ? "비밀번호를 확인해 주세요."
                                    : passwordCheckValid.runtimeType == String
                                        ? "비밀번호를 확인해 주세요."
                                        : "";
                    commonDialog(
                        context: context,
                        content: Container(
                            height: 50,
                            alignment: Alignment.bottomCenter,
                            child: Text(
                              alertMessage,
                              style: defaultTextStyle,
                            )),
                        onConfirm: () {},
                        barrierDismissible: true);
                    return;
                  } else {
                    await signatureDialog(
                        context: context,
                        onConfirm: (Uint8List image) async {
                          ref
                              .read(modalControllerProvider.notifier)
                              .isPopUp(visibility: true);
                          await ref.read(userAuthProvider.notifier).userJoin(
                              PostJoinBody(
                                  id: state is UserMinModel ? state.id : null,
                                  name: name!,
                                  userId: userId!,
                                  mobile: mobile!,
                                  password: password,
                                  image: image));
                          // if (isJoin is JoinFailedResponse) {
                          //   await commonDialog(
                          //     context: context,
                          //     title: '회원가입 실패',
                          //     content: Text(
                          //       isJoin.message,
                          //       style: defaultTextStyle,
                          //     ),
                          //     onConfirm: () {},
                          //   );
                          // } else {
                          //   print("ddd");
                          //   context.goNamed(RootTab.routeName);
                          // }
                          // print(value);
                        });
                  }
                },
                child: Text("전자 서명 추가 및 가입하기")),
          )
        ],
      ),
    );
  }

  _inputField(
      {String? inputValue,
      String? validation,
      String? title,
      String? placeholder,
      Function? onChangedValue,
      List<TextInputFormatter>? inputFormatters,
      bool obscureText = false,
      bool? enabled,
      final FormFieldValidator<String>? validator}) {
    return Padding(
      padding: const EdgeInsets.only(left: 20, right: 20, top: 5, bottom: 0),
      child: Column(
        children: [
          if (title != null)
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Container(
                  alignment: Alignment.bottomLeft,
                  child: Text(
                    title,
                    style: defaultTextStyle.copyWith(fontSize: 18),
                  )),
            ),
          SizedBox(
            height: 80,
            child: CustomTextFormField(
              onChanged: (value) {
                if (onChangedValue != null) onChangedValue(value);
              },
              value: inputValue,
              enabled: enabled ?? true,
              obscureText: obscureText,
              errorText: validation,
              hintText: placeholder ?? "입력해 주세요.",
              inputFormatters: inputFormatters,
              validator: validator,
            ),
          ),
        ],
      ),
    );
  }
}

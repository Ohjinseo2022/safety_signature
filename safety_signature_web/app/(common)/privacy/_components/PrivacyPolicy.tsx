import React from 'react'

const PrivacyPolicy = () => {
  return (
    <div className="relative min-h-screen px-16 pb-24 bg-gray-100 text-gray-900">
      <h1 className="text-center text-2xl font-bold py-12">개인정보처리방침</h1>

      {/* 본 방침의 공개 */}
      <section>
        <h2 className="text-xl font-semibold mt-8 mb-4">
          제1조(본 방침의 공개)
        </h2>
        <ul className="list-disc pl-5 space-y-2">
          <li>
            회사는 이용자가 언제든지 쉽게 본 방침을 확인할 수 있도록 홈페이지 첫
            화면 또는 첫 화면과의 연결화면을 통해 본 방침을 공개하고 있습니다.
          </li>
          <li>
            회사는 본 방침을 공개할 때 글자 크기, 색상 등을 활용하여 이용자가
            쉽게 확인할 수 있도록 합니다.
          </li>
        </ul>
      </section>

      {/* 본 방침의 변경 */}
      <section>
        <h2 className="text-xl font-semibold mt-8 mb-4">
          제2조(본 방침의 변경)
        </h2>
        <ul className="list-disc pl-5 space-y-2">
          <li>
            본 방침은 개인정보 관련 법령, 정부 지침, 서비스 정책 변경 등에 따라
            개정될 수 있습니다.
          </li>
          <li>
            회사는 본 방침을 개정할 경우 다음 방법 중 하나 이상으로 공지합니다:
            <ul className="list-decimal pl-8 space-y-1">
              <li>홈페이지 공지사항 또는 별도 창을 통한 공지</li>
              <li>전자우편, 서면, 모사전송 등의 방법</li>
            </ul>
          </li>
          <li>중요한 변경이 있을 경우 최소 30일 전에 공지합니다.</li>
        </ul>
      </section>

      {/* 회원 가입을 위한 정보 */}
      <section>
        <h2 className="text-xl font-semibold mt-8 mb-4">
          제3조(회원 가입을 위한 정보)
        </h2>
        <p>회사는 회원가입을 위해 다음 정보를 수집합니다.</p>
        <ul className="list-disc pl-5 space-y-2">
          <li>
            <strong className="underline">필수 수집 정보:</strong> 이메일 주소,
            이름, 닉네임, 생년월일, 휴대폰 번호
          </li>
        </ul>
      </section>

      {/* 개인정보의 이용 */}
      <section>
        <h2 className="text-xl font-semibold mt-8 mb-4">
          제7조(개인정보의 이용)
        </h2>
        <p>회사는 개인정보를 다음과 같은 목적으로 사용합니다.</p>
        <ul className="list-disc pl-5 space-y-2">
          <li>공지사항 전달 및 회사 운영</li>
          <li>이용문의 회신 및 불만 처리</li>
          <li>서비스 제공 및 운영</li>
          <li>이용 제한 조치 및 서비스 보호</li>
          <li>
            <strong className="underline">신규 서비스 개발</strong>
          </li>
        </ul>
      </section>

      {/* 개인정보의 보유 및 이용기간 */}
      <section>
        <h2 className="text-xl font-semibold mt-8 mb-4">
          제8조(개인정보의 보유 및 이용기간)
        </h2>
        <ul className="list-disc pl-5 space-y-2">
          <li>개인정보는 이용 목적이 달성된 후 지체 없이 파기됩니다.</li>
          <li>
            부정 이용 방지를 위해 회원 탈퇴 시점으로부터 최대 1년간 보관될 수
            있습니다.
          </li>
        </ul>
      </section>

      {/* 쿠키 관리 */}
      <section>
        <h2 className="text-xl font-semibold mt-8 mb-4">제19조(쿠키 관리)</h2>
        <p>웹브라우저 옵션을 통해 쿠키 허용, 차단 등을 설정할 수 있습니다.</p>
        <ul className="list-disc pl-5 space-y-2">
          <li>
            Chrome: 설정 &gt; 개인정보 및 보안 &gt; 쿠키 및 기타 사이트 데이터
          </li>
          <li>Edge: 설정 &gt; 쿠키 및 사이트 권한 &gt; 쿠키 관리</li>
        </ul>
      </section>

      {/* 권익 침해 구제 방법 */}
      <section>
        <h2 className="text-xl font-semibold mt-8 mb-4">
          제20조(권익침해에 대한 구제방법)
        </h2>
        <p>
          개인정보 침해 관련 분쟁 해결 및 상담을 원할 경우 다음 기관에 문의할 수
          있습니다.
        </p>
        <ul className="list-disc pl-5 space-y-2">
          <li>개인정보침해신고센터 (118 / privacy.kisa.or.kr)</li>
          <li>대검찰청 (1301 / www.spo.go.kr)</li>
          <li>경찰청 (182 / ecrm.cyber.go.kr)</li>
        </ul>
      </section>

      {/* 시행일 */}
      <p className="mt-10 font-semibold text-center">
        부칙: 본 방침은 <span className="underline">2025.03.18.</span>부터
        시행됩니다.
      </p>
    </div>
  )
}

export default PrivacyPolicy

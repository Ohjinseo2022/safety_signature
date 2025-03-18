'use client'

import { CommonCard } from '@/components/styled/common'
import PrivacyPolicy from './_components/PrivacyPolicy'

interface pageProps {}

const PrivarcyPage: React.FC<pageProps> = ({}) => {
  return (
    <CommonCard>
      <PrivacyPolicy></PrivacyPolicy>
    </CommonCard>
  )
}

export default PrivarcyPage

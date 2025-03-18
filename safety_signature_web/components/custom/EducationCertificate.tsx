'use client'

import { dateFormat } from '@/utils/utils'
import styled from 'styled-components'
import React from 'react'
import { ApproveMasterType } from '@/app/(admin)/bulletin/_hooks/BulletinBoardQuery'

interface EducationCertificateProps {
  ref?: any
  headers: { label: string; columns: string }[]
  siteName: string
  educationType: string
  educationDate: string
  participants: {
    evenData: Array<ApproveMasterType[]>
    oddData: Array<ApproveMasterType[]>
  }
}

const EducationCertificate: React.FC<EducationCertificateProps> = ({
  ref,
  siteName,
  headers,
  educationType,
  educationDate,
  participants,
}) => {
  console.log(participants)
  return (
    <Container ref={ref}>
      {/* 상단 테이블 */}
      <HeaderTable>
        <tbody>
          <tr>
            <LogoCell colSpan={2}>
              {/* TODO: 게시글 회사 로고가 나와야함! */}
              <img
                src={`${process.env.NEXT_PUBLIC_DOMAIN}${process.env.NEXT_PUBLIC_BASE_URL}/attach/download/ATTACH_001`}
              />
            </LogoCell>
            <SiteNameCell colSpan={2}>{siteName}</SiteNameCell>
          </tr>
          <tr>
            <TitleCell rowSpan={2} colSpan={2}>
              교육훈련 수강확인서
            </TitleCell>
            <InfoCell className="bold">교육종류</InfoCell>
            <InfoCell>{educationType}</InfoCell>
          </tr>
          <tr>
            <InfoCell className="bold">교육일시</InfoCell>
            <InfoCell>{dateFormat(educationDate, 'YYYY-MM-DD')}</InfoCell>
          </tr>
        </tbody>
      </HeaderTable>

      {/* 본문 테이블 */}
      <TableContainer>
        <StyledTable>
          <thead>
            <tr>
              {headers.map((header, idx) => (
                <Th key={`${header.columns}-${idx}`}>{header.label}</Th>
              ))}
              {headers.map((header, idx) => (
                <Th key={`${header.columns}-${idx}`}>{header.label}</Th>
              ))}
            </tr>
          </thead>
          <tbody>
            {participants.evenData &&
              participants.evenData.length > 0 &&
              participants.evenData.map((even, idx) => {
                return even.map((item, index) => {
                  return (
                    <tr key={`${item.id}-${index}`}>
                      {headers.map((header, i) => {
                        if (header.columns === 'index') {
                          return (
                            <Td key={header.columns}>{index + 1 + 23 * idx}</Td>
                          )
                        }
                        if (header.columns === 'attachDocId') {
                          return (
                            <Td key={`${header.columns}-${Math.random()}`}>
                              <SignatureImage
                                src={`${process.env.NEXT_PUBLIC_DOMAIN}${process.env.NEXT_PUBLIC_BASE_URL}/attach/download/${item.attachDocId}`}
                                alt="서명"
                              />
                            </Td>
                          )
                        }
                        return (
                          <Td key={`${header.columns}-${Math.random()}`}>
                            {item[header.columns]}
                          </Td>
                        )
                      })}
                      {participants.oddData[idx].length > 0
                        ? headers.map((header, i) => {
                            let odd = participants.oddData[idx][index]

                            if (header.columns === 'index') {
                              return (
                                <Td key={`${header.columns}-${Math.random()}`}>
                                  {index + 1 + 23 + 23 * idx}
                                </Td>
                              )
                            }
                            if (header.columns === 'attachDocId') {
                              return (
                                <Td key={`${header.columns}-${Math.random()}`}>
                                  <SignatureImage
                                    src={`${process.env.NEXT_PUBLIC_DOMAIN}${process.env.NEXT_PUBLIC_BASE_URL}/attach/download/${odd.attachDocId}`}
                                    alt="서명"
                                  />
                                </Td>
                              )
                            }
                            return (
                              <Td key={`${header.columns}-${Math.random()}`}>
                                {odd[header.columns]}
                              </Td>
                            )
                          })
                        : headers.map((header, i) => {
                            return (
                              <Td
                                key={`${header.columns}-${Math.random()}`}
                              ></Td>
                            )
                          })}
                    </tr>
                  )
                })
              })}
          </tbody>
        </StyledTable>
      </TableContainer>
    </Container>
  )
}

//    <>
//                       <Td></Td>
//                       <Td></Td>
//                       <Td></Td>
//                       <Td></Td>
//                       <Td></Td>
//                     </>

export default EducationCertificate

// Styled-components
const Container = styled.div`
  width: 100%;
  max-width: 1200px;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);
  margin: auto;
  text-align: center;
`

const HeaderTable = styled.table`
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 10px;
`

const LogoCell = styled.td`
  text-align: left;
  background-color: white;
  padding: 8px;
  border-right: 0px !important;
  img {
    height: 40px;
    margin-right: 5px;
  }
`

const TitleCell = styled.td`
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  background: #f5f5f5;
  color: black;
  padding: 10px;
`

const SiteNameCell = styled.td`
  font-size: 14px;
  text-align: right;
  padding: 8px;
  font-weight: bold;
  color: black;
  border-left: 0px !important;
  background-color: white;
`

const InfoCell = styled.td`
  font-size: 14px;
  text-align: center;
  padding: 5px;
  color: black;
  background: white;
  &.bold {
    font-weight: bold;
    background: #f5f5f5;
  }
`

const TableContainer = styled.div`
  width: 100%;
  overflow-x: auto;
`

const StyledTable = styled.table`
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
`

const Th = styled.th`
  background-color: #929292 !important;
  color: black;
  padding: 8px;
  text-align: center;
  border: 1px solid #ddd;
`

const Td = styled.td`
  padding: 8px;
  width: 10%;
  color: black;
  background-color: white !important;
  text-align: center;
  border: 1px solid black;
`

const SignatureImage = styled.img`
  width: 80px;
  height: 40px;
`

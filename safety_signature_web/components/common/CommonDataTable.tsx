'use client'

import styled from 'styled-components'
import { useEffect, useState } from 'react'

interface CommonDataTableProps {
  title: string
  onTopButtonClick?: (e: any) => void
  topBtnLable?: string
  headers: { label: string; columns: string }[]

  dataItem: any
}

const CommonDataTable: React.FC<CommonDataTableProps> = ({
  title,
  onTopButtonClick,
  topBtnLable,
  headers,
  dataItem,
}) => {
  return (
    <DataTableContainer>
      <DataTableHeader>
        <h3>{title}</h3>
        {topBtnLable && (
          <button onClick={onTopButtonClick}>{topBtnLable}</button>
        )}
      </DataTableHeader>

      <DataTable>
        <thead>
          <tr>
            {headers.map((header, idx) => (
              <th key={`${header.columns}-${idx}`}>{header.label}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {dataItem.map((item: any, idx: number) => (
            <tr key={`${item.id}-${idx}`}>
              {headers.map((header, index) => {
                if (header.columns === 'attachDocId') {
                  return (
                    <td
                      className="signature-cell"
                      key={`${header.columns}-${item.id}`}
                    >
                      <img
                        src={`${process.env.NEXT_PUBLIC_DOMAIN}/${process.env.NEXT_PUBLIC_BASE_URL}/attach/download/${item[header.columns]}`}
                      ></img>
                    </td>
                  )
                }

                return (
                  <td key={`${header.columns}-${item.id}`}>
                    {item[header.columns]}
                  </td>
                )
              })}
            </tr>
          ))}
        </tbody>
      </DataTable>
    </DataTableContainer>
  )
}

export default CommonDataTable

const DataTableContainer = styled.div`
  background-color: #1e1e1e;
  padding: 20px;
  border-radius: 8px;
`

const DataTableHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;

  h3 {
    margin: 0;
    font-size: 20px;
  }

  button {
    background-color: #60a5fa;
    color: #ffffff;
    border: none;
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;

    &:hover {
      background-color: #3b82f6;
    }
  }
`

// const DataTable = styled.table`
//   width: 100%;
//   border-collapse: collapse;
//   text-align: left;
//   font-size: 14px;

//   thead {
//     background-color: #333333;

//     th {
//       padding: 10px;
//       border: 1px solid #444444;
//       color: #ffffff;
//     }
//   }

//   tbody {
//     tr {
//       &:hover {
//         background-color: #2a2a2a !important;
//       }
//       img {
//         height: 50px;
//         object-fit: scale-down;
//       }
//       td {
//         &:hover {
//           background-color: #2a2a2a !important;
//         }
//         padding: 10px;
//         border: 1px solid #444444;
//         color: #b0b0b0;
//       }
//     }
//   }
// `

const DataTable = styled.table`
  width: 100% !important;
  border-collapse: collapse !important;
  text-align: left !important;
  font-size: 14px !important;
  background-color: #ffffff !important; /* ✅ 테이블 전체 배경 흰색 */

  thead {
    background-color: #333333;
    color: #ffffff !important;

    th {
      padding: 12px !important;
      border: 1px solid #444444;
      font-weight: bold !important;
      text-align: center !important;
    }
  }

  tbody {
    tr:nth-child(even) {
      background-color: #f9f9f9 !important; /* ✅ 짝수 행 배경 */
    }

    tr:nth-child(odd) {
      background-color: #e6e6e6 !important; /* ✅ 홀수 행 배경 */
    }

    tr:hover {
      background-color: #d0d0d0 !important; /* ✅ 호버 시 색상 */
    }

    td {
      padding: 12px !important;
      border: 1px solid #dddddd !important; /* ✅ 테두리 색상 */
      color: #333333 !important; /* ✅ 텍스트 색상 */
      text-align: center !important;
      background-color: transparent !important;
    }

    /* 서명 이미지 스타일 */
    td.signature-cell {
      background-color: transparent !important;
      padding: 0 !important;
      text-align: center !important;

      img {
        width: 50px !important;
        height: auto !important;
        display: block !important;
        margin: auto !important;
      }
    }
  }
`

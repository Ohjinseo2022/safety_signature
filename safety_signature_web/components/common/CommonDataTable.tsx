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
          {dataItem.map((item: any) => (
            <tr key={item.id}>
              {headers.map((header, index) => (
                <td key={`${header.columns}-${item.id}`}>
                  {item[header.columns]}
                </td>
              ))}
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

const DataTable = styled.table`
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  font-size: 14px;

  thead {
    background-color: #333333;

    th {
      padding: 10px;
      border: 1px solid #444444;
      color: #ffffff;
    }
  }

  tbody {
    tr {
      &:hover {
        background-color: #2a2a2a;
      }

      td {
        padding: 10px;
        border: 1px solid #444444;
        color: #b0b0b0;
      }
    }
  }
`

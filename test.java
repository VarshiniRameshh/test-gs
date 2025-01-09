Imports ClosedXML.Excel

Module Module1
    Sub Main()
        ' Path to the Excel file
        Dim filePath As String = "C:\path\to\your\file.xlsx"

        ' Dictionary to store the counts of each status
        Dim statusCounts As New Dictionary(Of String, Integer)

        ' Load the Excel file
        Using workbook As New XLWorkbook(filePath)
            ' Get the first worksheet
            Dim worksheet = workbook.Worksheet(1)

            ' Find the column number for "Status"
            Dim statusColumnNumber As Integer = -1
            Dim headers = worksheet.Row(1).Cells() ' Assuming the first row contains headers
            For Each cell In headers
                If cell.Value.ToString().Trim().ToLower() = "status" Then
                    statusColumnNumber = cell.Address.ColumnNumber
                    Exit For
                End If
            Next

            If statusColumnNumber = -1 Then
                Console.WriteLine("Status column not found!")
                Return
            End If

            ' Read the Status column and count occurrences
            Dim rowCount = worksheet.RowsUsed().Count()
            For i As Integer = 2 To rowCount ' Assuming data starts from row 2
                Dim status = worksheet.Cell(i, statusColumnNumber).Value.ToString().Trim()
                If Not String.IsNullOrEmpty(status) Then
                    If statusCounts.ContainsKey(status) Then
                        statusCounts(status) += 1
                    Else
                        statusCounts(status) = 1
                    End If
                End If
            Next
        End Using

        ' Display the results
        Console.WriteLine("Status Counts:")
        For Each kvp In statusCounts
            Console.WriteLine($"{kvp.Key}: {kvp.Value}")
        Next

        ' Pause for user to see the output
        Console.WriteLine("Press any key to exit...")
        Console.ReadKey()
    End Sub
End Module

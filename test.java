Sub Main()
    ' Path to the Excel file
    Dim filePath As String
    filePath = "C:\path\to\your\file.xlsx" ' Update with your file path

    ' Dictionary to store the "Status" count
    Dim StatusCount As Object
    Set StatusCount = CreateObject("Scripting.Dictionary")

    ' Load the Excel file
    Dim wb As Workbook
    Dim ws As Worksheet
    Set wb = Workbooks.Open(filePath)
    Set ws = wb.Sheets(1)

    ' Loop through the data rows
    Dim lastRow As Long
    lastRow = ws.Cells(ws.Rows.Count, 1).End(xlUp).Row ' Assuming column A has data

    Dim status As String
    Dim i As Long

    For i = 2 To lastRow ' Assuming row 1 contains headers
        status = ws.Cells(i, 3).Value ' Adjust column number to your "Status" column

        If Not IsEmpty(status) Then
            If StatusCount.exists(status) Then
                StatusCount(status) = StatusCount(status) + 1
            Else
                StatusCount.Add status, 1
            End If
        End If
    Next i

    ' Display the results
    Dim key As Variant
    For Each key In StatusCount.Keys
        Debug.Print key & ": " & StatusCount(key) ' Outputs to Immediate Window (Ctrl+G)
    Next key

    ' Close the workbook without saving
    wb.Close False

    ' Cleanup
    Set StatusCount = Nothing
    Set wb = Nothing
    Set ws = Nothing
End Sub

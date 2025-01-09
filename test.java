Dim objExcel, objWorkbook, objSheet, objRange, objFilteredRange, objFileSystem, objOutputFile
Dim sourceFile, outputFile, filterColumn, filterCriteria, cellValue, row, column

' Define file paths and settings
sourceFile = "C:\path\to\your\inputfile.xlsx"  ' Replace with your actual Excel file path
outputFile = "C:\path\to\your\outputfile.txt" ' Replace with your desired output file path
filterColumn = 3  ' Replace with the correct column index (e.g., 3 for "Status")
filterCriteria = "Onboarded"  ' Replace with your desired filter criteria

' Create Excel application object
Set objExcel = CreateObject("Excel.Application")
objExcel.Visible = False
objExcel.DisplayAlerts = False

' Open the workbook and select the first worksheet
Set objWorkbook = objExcel.Workbooks.Open(sourceFile)
Set objSheet = objWorkbook.Worksheets(1)

' Define the range to filter (Assumes data starts at A1)
Set objRange = objSheet.UsedRange

' Check if data exists
If objRange Is Nothing Or objRange.Columns.Count < filterColumn Then
    WScript.Echo "Error: Data not found or invalid column index."
    objWorkbook.Close False
    objExcel.Quit
    WScript.Quit
End If

' Apply AutoFilter
On Error Resume Next
objRange.AutoFilter filterColumn, filterCriteria
If Err.Number <> 0 Then
    WScript.Echo "Error: Unable to apply filter. Check the column index and criteria."
    objWorkbook.Close False
    objExcel.Quit
    WScript.Quit
End If
On Error GoTo 0

' Get filtered data
Set objFilteredRange = objSheet.UsedRange.SpecialCells(12, 2) ' Get visible cells after filtering

' Create a text file and write filtered data
Set objFileSystem = CreateObject("Scripting.FileSystemObject")
Set objOutputFile = objFileSystem.CreateTextFile(outputFile, True)

For Each row In objFilteredRange.Rows
    For Each column In row.Columns
        cellValue = column.Value
        If IsNull(cellValue) Then
            cellValue = ""
        End If
        objOutputFile.Write cellValue & vbTab  ' Tab-separated
    Next
    objOutputFile.WriteLine
Next

' Close the text file
objOutputFile.Close

' Clean up
objWorkbook.Close False
objExcel.Quit
Set objFilteredRange = Nothing
Set objRange = Nothing
Set objSheet = Nothing
Set objWorkbook = Nothing
Set objFileSystem = Nothing
Set objOutputFile = Nothing
Set objExcel = Nothing

WScript.Echo "Filtering complete. Output saved to: " & outputFile

Dim objExcel, objWorkbook, objSheet, objRange, objFilteredRange, objFileSystem, objOutputFile
Dim sourceFile, outputFile, filterColumn, filterCriteria

' Define file paths and settings
sourceFile = "C:\path\to\your\inputfile.xlsx"  ' Replace with your input file path
outputFile = "C:\path\to\your\outputfile.txt" ' Replace with your output text file path
filterColumn = 10  ' Column to filter (adjust as needed)
filterCriteria = "Onboarded"  ' Set the filter criteria

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
If objRange Is Nothing Then
    WScript.Echo "Error: No data found in the worksheet."
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

' Write filtered data to a text file
Set objFileSystem = CreateObject("Scripting.FileSystemObject")
Set objOutputFile = objFileSystem.CreateTextFile(outputFile, True)

For Each row In objFilteredRange.Rows
    For Each column In row.Columns
        If Not IsNull(column.Value) Then
            objOutputFile.Write column.Value & vbTab ' Tab-separated values
        End If
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

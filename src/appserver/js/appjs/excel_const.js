/** 
* 
*Microsoft Excel 常量 
*参阅特性本主题列出了 Microsoft Excel 对象模型中的所有常量。 
*/ 

Excel = function(){ 
    return {   
		XlBoolean : {
			True : true,
			False : false
		
		},
        XlApplicationInternational:{ 
                xl24HourClock: 33 , 
                xl4DigitYears: 43 , 
                xlAlternateArraySeparator: 16 , 
                xlColumnSeparator: 14,  
                xlCountryCode:  1,  
                xlCountrySetting:  2 , 
                xlCurrencyBefore:  37 , 
                xlCurrencyCode:  25 , 
                xlCurrencyDigits:  27,  
                xlCurrencyLeadingZeros:  40 , 
                xlCurrencyMinusSign:  38 , 
                xlCurrencyNegative:  28 , 
                xlCurrencySpaceBefore:  36 , 
                xlCurrencyTrailingZeros:  39 , 
                xlDateOrder:  32 , 
                xlDateSeparator:  17 , 
                xlDayCode:  21 , 
                XlDayLeadingZero:  42 , 
                xlDecimalSeparator:  3 , 
                xlGeneralFormatName:  26 , 
                xlHourCode:  22 , 
                xlLeftBrace:  12 , 
                xlLeftBracket:  10 , 
                xlListSeparator:  5 , 
                xlLowerCaseColumnLetter:  9 , 
                xlLowerCaseRowLetter:  8 , 
                xlMDY:  44 , 
                xlMetric:  35 , 
                xlMinuteCode:  23 , 
                xlMonthCode:  20 , 
                xlMonthLeadingZero:  41 , 
                xlMonthNameChars:  30 , 
                xlNoncurrencyDigits:  29 , 
                xlNonEnglishFunctions:  34 , 
                xlRightBrace:  13 , 
                xlRightBracket:  11 , 
                xlRowSeparator:  15 , 
                xlSecondCode:  24 , 
                xlThousandsSeparator:  4 , 
                xlTimeLeadingZero:  45 , 
                xlTimeSeparator:  18 , 
                xlUpperCaseColumnLetter:  7 , 
                xlUpperCaseRowLetter:  6 , 
                xlWeekdayNameChars:  31 , 
                xlYearCode:  19 
			}, 

            xlApplyNamesOrder:{ 
                xlColumnThenRow:  2 , 
                xlRowThenColumn:  1 
			}, 
                 
            XlArabicModes:{ 
                xlArabicBothStrict:  3 , 
                xlArabicNone:  0 , 
                xlArabicStrictAlefHamza:  1 , 
                xlArabicStrictFinalYaa:  2 
			}, 

            XlArrangeStyle:{ 
                xlArrangeStyleCascade:  7 , 
                xlArrangeStyleHorizontal:  -4128 , 
                xlArrangeStyleTiled:  1 , 
                xlArrangeStyleVertical:  -4166 
			}, 

            XlArrowHeadLength:{ 
                xlArrowHeadLengthLong:  3 , 
                xlArrowHeadLengthMedium:  -4138 , 
                xlArrowHeadLengthShort:  1 
			}, 

            XlArrowHeadStyle:{ 
                xlArrowHeadStyleClosed:  3 , 
                xlArrowHeadStyleDoubleClosed:  5 , 
                xlArrowHeadStyleDoubleOpen:  4 , 
                xlArrowHeadStyleNone:  -4142 , 
                xlArrowHeadStyleOpen:  2 
			}, 

            XlArrowHeadWidth:{ 
                xlArrowHeadWidthMedium:  -4138 , 
                xlArrowHeadWidthNarrow:  1 , 
                xlArrowHeadWidthWide:  3 
			}, 

			XlAutoFillType:{ 
				xlFillCopy:  1 , 
				xlFillDays:  5 , 
				xlFillDefault:  0 , 
				xlFillFormats:  3 , 
				xlFillMonths:  7 , 
				xlFillSeries:  2 , 
				xlFillValues:  4 , 
				xlFillWeekdays:  6 , 
				xlFillYears:  8 , 
				xlGrowthTrend:  10 , 
				xlLinearTrend:  9 
			}, 

			XlAutoFilterOperator:{ 
				xlAnd:  1 , 
				xlBottom10Items:  4 , 
				xlBottom10Percent:  6 , 
				xlOr:  2 , 
				xlTop10Items:  3 , 
				xlTop10Percent:  5 
			}, 

			XlAxisCrosses:{ 
				xlAxisCrossesAutomatic:  -4105 , 
				xlAxisCrossesCustom:  -4114 , 
				xlAxisCrossesMaximum:  2 , 
				xlAxisCrossesMinimum:  4 
			}, 

			XlAxisGroup:{ 
				xlPrimary:  1 , 
				xlSecondary:  2 
			}, 

				XlAxisType:{ 
				xlCategory:  1 , 
				xlSeriesAxis:  3 , 
				xlValue:  2 
			}, 

				XlBackground:{ 
				xlBackgroundAutomatic:  -4105 , 
				xlBackgroundOpaque:  3 , 
				xlBackgroundTransparent:  2 
			}, 

			XlBarShape:{ 
				xlBox:  0 , 
				xlConeToMax:  5 , 
				xlConeToPoint:  4 , 
				xlCylinder:  3 , 
				xlPyramidToMax:  2 , 
				xlPyramidToPoint:  1 
			}, 

			XlBordersIndex:{ 

				xlDiagonalDown:  5 , 
				xlDiagonalUp:  6 , 
				xlEdgeBottom:  9 , 
				xlEdgeLeft:  7 , 
				xlEdgeRight:  10 , 
				xlEdgeTop:  8 , 
				xlInsideHorizontal:  12 , 
				xlInsideVertical:  11 
			}, 

			XlBorderWeight:{ 
					xlHairline:  1 , 
					xlMedium:  -4138 , 
					xlThick:  4 , 
					xlThin:  2
			}, 

			XlCalculatedMemberType:{ 
				xlCalculatedMember:  0 , 
				xlCalculatedSet:  1 
			}, 

			XlCalculation:{ 
				xlCalculationAutomatic:  -4105 , 
				xlCalculationManual:  -4135 , 
				xlCalculationSemiautomatic:  2 
			}, 

			XlCalculationInterruptKey:{ 
				xlAnyKey:  2 , 
				xlEscKey:  1 , 
				xlNoKey:  0 
			}, 

			XlCalculationState:{ 
				xlCalculating:  1 , 
				xlDone:  0 , 
				xlPending:  2 
			}, 

			XlCategoryType:{ 
				xlAutomaticScale:  -4105 , 
				xlCategoryScale:  2 , 
				xlTimeScale:  3 
			}, 

			XlCellInsertionMode:{ 
				xlInsertDeleteCells:  1 , 
				xlInsertEntireRows:  2 , 
				xlOverwriteCells:  0 
			}, 

			XlCellType:{ 
				xlCellTypeAllFormatConditions:  -4172 , 
				xlCellTypeAllValidation:  -4174 , 
				xlCellTypeBlanks : 4 , 
				xlCellTypeComments:  -4144 , 
				xlCellTypeConstants:  2 , 
				xlCellTypeFormulas:  -4123 , 
				xlCellTypeLastCell:  11 , 
				xlCellTypeSameFormatConditions : -4173 , 
				xlCellTypeSameValidation:  -4175 , 
				xlCellTypeVisible:  12 
			}, 

			XlClipboardFormat:{ 
				xlClipboardFormatBIFF:  8 , 
				xlClipboardFormatBIFF2:  18 , 
				xlClipboardFormatBIFF3:  20 , 
				xlClipboardFormatBIFF4:  30 , 
				xlClipboardFormatBinary:  15 , 
				xlClipboardFormatBitmap:  9 , 
				xlClipboardFormatCGM:  13 , 
				xlClipboardFormatCSV:  5 , 
				xlClipboardFormatDIF:  4 , 
				xlClipboardFormatDspText:  12 , 
				xlClipboardFormatEmbeddedObject:  21 , 
				xlClipboardFormatEmbedSource:  22 , 
				xlClipboardFormatLink:  11 , 
				xlClipboardFormatLinkSource:  23 , 
				xlClipboardFormatLinkSourceDesc:  32 , 
				xlClipboardFormatMovie:  24 , 
				xlClipboardFormatNative:  14 , 
				xlClipboardFormatObjectDesc:  31 , 
				xlClipboardFormatObjectLink:  19 , 
				xlClipboardFormatOwnerLink:  17 , 
				xlClipboardFormatPICT:  2 , 
				xlClipboardFormatPrintPICT:  3 , 
				xlClipboardFormatRTF:  7 , 
				xlClipboardFormatScreenPICT:  29 , 
				xlClipboardFormatStandardFont:  28 , 
				xlClipboardFormatStandardScale:  27 , 
				xlClipboardFormatSYLK:  6 , 
				xlClipboardFormatTable:  16 , 
				xlClipboardFormatText:  0 , 
				xlClipboardFormatToolFace:  25 , 
				xlClipboardFormatToolFacePICT:  26 , 
				xlClipboardFormatVALU:  1 , 
				xlClipboardFormatWK1:  10 
			}, 

			XlCmdType:{ 
				xlCmdCube:  1 , 
				xlCmdDefault:  4 , 
				xlCmdList:  5 , 
				xlCmdSql:  2 , 
				xlCmdTable:  3 
			}, 

			XlColorIndex:{ 
				xlColorIndexAutomatic:  -4105 , 
				xlColorIndexNone:  -4142 
			}, 

			XlColumnDataType:{ 
				xlDMYFormat:  4 , 
				xlDYMFormat:  7 , 
				xlEMDFormat:  10 , 
				xlGeneralFormat:  1 , 
				xlMDYFormat:  3 , 
				xlMYDFormat:  6 , 
				xlSkipColumn:  9 , 
				xlTextFormat:  2 , 
				xlYDMFormat:  8 , 
				xlYMDFormat:  5 
			}, 

			XlCommandUnderlines:{ 
				xlCommandUnderlinesAutomatic:  -4105 , 
				xlCommandUnderlinesOff:  -4146 , 
				xlCommandUnderlinesOn:  1 
			}, 

			XlHAlign:{ 
				xlHAlignCenter:  -4108 , 
				xlHAlignCenterAcrossSelection:  7 , 
				xlHAlignDistributed:  -4117 , 
				xlHAlignFill:  5 , 
				xlHAlignGeneral:  1 , 
				xlHAlignJustify:  -4130 , 
				xlHAlignLeft:  -4131 , 
				xlHAlignRight:  -4152 
			}, 
 
			XlLineStyle:{ 
				xlContinuous:  1 , 
				xlDash:  -4115 , 
				xlDashDot:  4 , 
				xlDashDotDot:  5 , 
				xlDot:  -4118 , 
				xlDouble:  -4119 , 
				xlLineStyleNone:  -4142 , 
				xlSlantDashDot:  13 
			}, 

			XlObjectSize:{ 
				xlFitToPage:  2 , 
				xlFullPage:  3 , 
				xlScreenSize:  1 
			}, 

			XlOrientation:{ 
				xlDownward:  -4170 , 
				xlHorizontal:  -4128 , 
				xlUpward : -4171 , 
				xlVertical:  -4166 
			}, 

			XlPageBreak:{ 
				xlPageBreakAutomatic:  -4105 , 
				xlPageBreakManual:  -4135 , 
				xlPageBreakNone: -4142 
			}, 

			XlPageBreakExtent:{ 
				xlPageBreakFull:  1 , 
				xlPageBreakPartial:  2 
			}, 

			XlPageOrientation:{ 
				xlLandscape:  2 , 
				xlPortrait:  1 
			}, 

			XlPaperSize:{ 
				xlPaper10x14:  16 , 
				xlPaper11x17:  17 , 
				xlPaperA3:  8 , 
				xlPaperA4:  9 , 
				xlPaperA4Small:  10 , 
				xlPaperA5:  11 , 
				xlPaperB4:  12 , 
				xlPaperB5:  13 , 
				xlPaperCsheet:  24 , 
				xlPaperDsheet:  25 , 
				xlPaperEnvelope10:  20 , 
				xlPaperEnvelope11:  21 , 
				xlPaperEnvelope12:  22 , 
				xlPaperEnvelope14:  23 , 
				xlPaperEnvelope9:  19 , 
				xlPaperEnvelopeB4:  33 , 
				xlPaperEnvelopeB5:  34 , 
				xlPaperEnvelopeB6:  35 , 
				xlPaperEnvelopeC3:  29 , 
				xlPaperEnvelopeC4:  30 , 
				xlPaperEnvelopeC5:  28 , 
				xlPaperEnvelopeC6:  31 , 
				xlPaperEnvelopeC65:  32 , 
				xlPaperEnvelopeDL:  27 , 
				xlPaperEnvelopeItaly:  36 , 
				xlPaperEnvelopeMonarch:  37 , 
				xlPaperEnvelopePersonal:  38 , 
				xlPaperEsheet:  26 , 
				xlPaperExecutive:  7 , 
				xlPaperFanfoldLegalGerman:  41 , 
				xlPaperFanfoldStdGerman:  40 , 
				xlPaperFanfoldUS:  39 , 
				xlPaperFolio:  14 , 
				xlPaperLedger:  4 , 
				xlPaperLegal:  5 , 
				xlPaperLetter:  1 , 
				xlPaperLetterSmall:  2 , 
				xlPaperNote:  18 , 
				xlPaperQuarto:  15 , 
				xlPaperStatement:  6 , 
				xlPaperTabloid:  3 , 
				xlPaperUser:  256 
			}, 

			XlParameterDataType:{ 
				xlParamTypeBigInt:  -5 , 
				xlParamTypeBinary:  -2 , 
				xlParamTypeBit:  -7 , 
				xlParamTypeChar:  1 , 
				xlParamTypeDate:  9 , 
				xlParamTypeDecimal:  3 , 
				xlParamTypeDouble:  8 , 
				xlParamTypeFloat:  6 , 
				xlParamTypeInteger:  4 , 
				xlParamTypeLongVarBinary:  -4 , 
				xlParamTypeLongVarChar:  -1 , 
				xlParamTypeNumeric:  2 , 
				xlParamTypeReal:  7 , 
				xlParamTypeSmallInt:  5 , 
				xlParamTypeTime:  10 , 
				xlParamTypeTimestamp:  11 , 
				xlParamTypeTinyInt:  -6 , 
				xlParamTypeUnknown:  0 , 
				xlParamTypeVarBinary:  -3 , 
				xlParamTypeVarChar:  12 , 
				xlParamTypeWChar:  -8 
			}, 

			XlParameterType:{ 
				xlConstant:  1 , 
				xlPrompt:  0 , 
				xlRange:  2 
			}, 

			XlPasteSpecialOperation:{ 
				xlPasteSpecialOperationAdd:  2 , 
				xlPasteSpecialOperationDivide:  5 , 
				xlPasteSpecialOperationMultiply:  4 , 
				xlPasteSpecialOperationNone:  -4142 , 
				xlPasteSpecialOperationSubtract:  3 
			}, 

			XlPasteType:{ 
				xlPasteAll:  -4104 , 
				xlPasteAllExceptBorders:  7 , 
				xlPasteColumnWidths:  8 , 
				xlPasteComments:  -4144 , 
				xlPasteFormats:  -4122 , 
				xlPasteFormulas:  -4123 , 
				xlPasteFormulasAndNumberFormats:  11 , 
				xlPasteValidation : 6 , 
				xlPasteValues:  -4163 , 
				xlPasteValuesAndNumberFormats:  12 
			}, 

			XlPrintErrors:{ 
				xlPrintErrorsBlank:  1 , 
				xlPrintErrorsDash:  2 , 
				xlPrintErrorsDisplayed:  0 , 
				xlPrintErrorsNA:  3 
			}, 

			XlPrintLocation:{ 
				xlPrintInPlace:  16 , 
				xlPrintNoComments:  -4142 , 
				xlPrintSheetEnd:  1 
			}, 

			XlRangeAutoFormat:{ 
				xlRangeAutoFormat3DEffects1:  13 , 
				xlRangeAutoFormat3DEffects2:  14 , 
				xlRangeAutoFormatAccounting1:  4 , 
				xlRangeAutoFormatAccounting2:  5 , 
				xlRangeAutoFormatAccounting3:  6 , 
				xlRangeAutoFormatAccounting4:  17 , 
				xlRangeAutoFormatClassic1:  1 , 
				xlRangeAutoFormatClassic2:  2 , 
				xlRangeAutoFormatClassic3:  3 , 
				xlRangeAutoFormatClassicPivotTable:  31 , 
				xlRangeAutoFormatColor1:  7 , 
				xlRangeAutoFormatColor2:  8 , 
				xlRangeAutoFormatColor3:  9 , 
				xlRangeAutoFormatList1:  10 , 
				xlRangeAutoFormatList2:  11 , 
				xlRangeAutoFormatList3:  12 , 
				xlRangeAutoFormatLocalFormat1:  15 , 
				xlRangeAutoFormatLocalFormat2:  16 , 
				xlRangeAutoFormatLocalFormat3:  19 , 
				xlRangeAutoFormatLocalFormat4:  20 , 
				xlRangeAutoFormatNone:  -4142 , 
				xlRangeAutoFormatPTNone:  42 , 
				xlRangeAutoFormatReport1:  21 , 
				xlRangeAutoFormatReport10:  30 , 
				xlRangeAutoFormatReport2:  22 , 
				xlRangeAutoFormatReport3:  23 , 
				xlRangeAutoFormatReport4:  24 , 
				xlRangeAutoFormatReport5:  25 , 
				xlRangeAutoFormatReport6:  26 , 
				xlRangeAutoFormatReport7:  27 , 
				xlRangeAutoFormatReport8:  28 , 
				xlRangeAutoFormatReport9:  29 , 
				xlRangeAutoFormatSimple:  -4154 , 
				xlRangeAutoFormatTable1:  32 , 
				xlRangeAutoFormatTable10:  41 , 
				xlRangeAutoFormatTable2:  33 , 
				xlRangeAutoFormatTable3:  34 , 
				xlRangeAutoFormatTable4:  35 , 
				xlRangeAutoFormatTable5:  36 , 
				xlRangeAutoFormatTable6:  37 , 
				xlRangeAutoFormatTable7:  38 , 
				xlRangeAutoFormatTable8:  39 , 
				xlRangeAutoFormatTable9:  40 
			}, 

			XlSheetType:{ 
				xlChart:  -4109 , 
				xlDialogSheet:  -4116 , 
				xlExcel4IntlMacroSheet:  4 , 
				xlExcel4MacroSheet:  3 , 
				xlWorksheet:  -4167 
			}, 

			XlUnderlineStyle:{ 
				xlUnderlineStyleDouble:  -4119 , 
				xlUnderlineStyleDoubleAccounting:  5 , 
				xlUnderlineStyleNone:  -4142 , 
				xlUnderlineStyleSingle:  2 , 
				xlUnderlineStyleSingleAccounting:  4 
			}, 

			XlVAlign:{ 
				xlVAlignBottom:  -4107 , 
				xlVAlignCenter:  -4108 , 
				xlVAlignDistributed:  -4117 , 
				xlVAlignJustify:  -4130 , 
				xlVAlignTop:  -4160 
			}, 

			XlWBATemplate:{ 
				xlWBATChart:  -4109 , 
				xlWBATExcel4IntlMacroSheet:  4 , 
				xlWBATExcel4MacroSheet:  3 , 
				xlWBATWorksheet:  -4167 
			
			}

		}; 

	} 
();
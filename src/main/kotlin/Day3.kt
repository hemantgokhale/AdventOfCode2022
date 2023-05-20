fun solveDay3() {
    // part 1
    assert(getTotalRucksackPriority(testInput) == 157)
    assert(getTotalRucksackPriority(realInput) == 7997)

    // part 2
    assert(getTotalBadgePriority(testInput) == 70)
    assert(getTotalBadgePriority(realInput) == 2545)
}

fun getTotalBadgePriority(input: String): Int {
    var sacks = input.lineSequence().map { Rucksack(it) }.toMutableList()
    val sackGroups = mutableListOf<SackGroup>()
    while (sacks.isNotEmpty()) {
        sackGroups.add(SackGroup(sacks.take(3)))
        sacks = sacks.drop(3).toMutableList()
    }

    return sackGroups
        .map { it.commonItem }
        .sumOf { priority[it] ?: 0 }
}

fun getTotalRucksackPriority(input: String) = input
    .lineSequence()
    .map { Rucksack(it).commonItem }
    .sumOf { priority[it] ?: 0 }

class SackGroup(sacks: List<Rucksack>) {
    val commonItem: Char

    init {
        commonItem = sacks.fold(sacks.first().contents.toSet()) { acc, rucksack ->
            acc.toSet().intersect(rucksack.contents.toSet())
        }.first()
    }
}

class Rucksack(val contents: String) {
    val compartment1: String
    val compartment2: String
    val commonItem: Char

    init {
        compartment1 = contents.take(contents.length / 2)
        compartment2 = contents.takeLast(contents.length / 2)
        commonItem = compartment1.toSet().intersect(compartment2.toSet()).first()
    }
}

private val priority = buildMap(52) {
    var p = 1
    for (c in 'a'..'z') {
        put(c, p++)
    }
    for (c in 'A'..'Z') {
        put(c, p++)
    }
}

private val testInput = """
    vJrwpWtwJgWrhcsFMMfFFhFp
    jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
    PmmdzqPrVvPwwTWBwg
    wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
    ttgJtRGJQctTZtZT
    CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()

private val realInput = """
    lvcNpRHDCnTLCJlL
    RFZggsMrjTFGCJmdmd
    srsBZgBqwBqRZbzqtHpzzDNtHDqV
    CCTPpCvlpzzZQQQflrzbQDttTJcgcggJcHtcddtdhT
    nMLBRnGsFFLznRFRLMMNBnNLDRDdhScJccctdSccJJgDDHhH
    GVBGVBsLjsrrvfzpjpfQ
    dzVRSPVVBVDSPzDBQVSQFFlrclMplpMJMtPJlJvHZCMt
    TjmGmbhjTnTmwhmrvvrHcZvCHZMl
    fnLwwqfwfqjghHwGThwfTGGBFVDFFsszSRVzRBsdBDgFsV
    CCWFCcdDWwcWFpSvggnzRRQszngJwT
    mGtqqLrqfmmLNtNrgTjgJzNQlvJTvznJ
    tnhVbhMLLZZrnWHPSHDBWbWBFd
    nQhvgnCQjSSSTTSMCsLDsfPfDlsPJMWLzL
    qrqBFFBbrVRLszLfsqdqPW
    bNFFRbBcFZNrZRRRbprNpFrHSwznTnvSwgHvzCSSSjnCQwgz
    tnnZZVmwmqtvVdZqnddQQHHTHQLsFTnsPrrgrQ
    MzMflMGpzGzPGPgjLgHrGj
    zPfhMJDDMJfzlhcRJvVwcVtwVcmcbqqtbv
    GVzrBVcPVfGrzVVBcQJlGGRCZSSRtSdRnGLJ
    wbjvHWbLvhFppjZdtwZRNddtJwlR
    pvMmbpFFbqqqvWHMFvzrLDMMrMTTrVTPzVzc
    qPmgpmwpwqWWPHdjdTNStzNLMztSWtMNtz
    lVFfJrFJbbcsvcRVRZzQCzQNSZTZ
    DGbvFSDGbDjnqgjwmGdq
    DMnpnpwwnpmRRmcRBDnDwpbRQHssHqhHCHHSsQddHZQQcqqs
    JlZjjlJgNSddfsgQdf
    GvrWvzNjvPVLDpbPZwBP
    drQDzHsHrdZWqDSSPwmmJDDbvbSJ
    hphBhCMFlBtBtGTJMJsscPwTjMJv
    tlBCGFgVFNpGClFFVGGtFBZrdZQznfdQQrRWVRQdRsVf
    NjdCLdjzzlNdjwBBtZqpqPJQbN
    CsDWcHcGHtcBbJPpbP
    mGHSssSgSsHFSgGrSgmlLzCdldllrCVCLdnfnT
    rDLLzRmbcLJRtRSvSBdZtSTp
    MFswshwgsCsjghgFBsGssjlZpfpvdSHfTdCZTSpHtfddTH
    llwlwGjMPMQQnBMswsFgglPVcWcDcbWqLWbbLJVDzrqnVr
    pqmmcSTLfSSSMFlf
    rHWtPWnHtlrlDntzWwtBFdzCFMRCfjRQFfgMRMjC
    PWWHDVZPDDJVlWHncGGbqqTVvVmpGTmm
    wLBtWhGWJBdMmZMs
    jgvNCFvvGppGnmNJ
    DDRQTgcvjTPFqGHhRVhLRSVL
    tPPwLpBpDpgLSPvgQCvsLPjdjNZrJZsdZjsrsnZNjbZc
    lMWzWMBhmMhRGfVRffHmMjJcJjrNNZnjJcWjNqJnZJ
    mFMzhTmBGfHTwgPgtptFpPgP
    qCcqJQHslgtsQsCZmPWNSRNZTPBBCN
    nnLpjjnvwwvDnrGwFvbFjwPgPWRTPrrPShNhmmNSRRPN
    bnwdDLjnzGgvFqdJQcqVfQVqHt
    DfCzDCCTDLDBCsdjzwdrHjbRgjGH
    MSStMScccJtPptJNJZtJJSrFdwPGjFFHHwsggrwdwRdP
    nStlpVlhhNSshZlcNZnMcctpBChWBQLqCWqmqvmBCBmQBqmL
    RfLHNvfLfLZQBtRZsBfffjVqGvqpGSmJpgrJpjGjrp
    FDbPCMzbTTDDPmzrVzqppJBrBj
    DPcWPWDhlbCcWBsQZZfHtdwf
    fbHfPfHHfPZWgZfSGpqNBqdBBjpjdPBJqv
    rnVNCwwrhhDrmmvcmjdDqcmB
    hFRrslFRNhFzVthllRCRCCwnQtSGfQgZZbbSWQLSSTZWbQTt
    nmVqTFCmTVbnvVCnqwFrffjhZLffhNrNJF
    StBHWDgMBpHMBHDzLjffjWwZJNNfNZjL
    wBBcDcgzdVbbQcnQlq
    MfGCtqGDhjDqHhrjGCcJZZBwHRcspZsBsHRc
    PFFpLFSpzVdSTPgnzzdPPZBRcZBwBJRcWJBmJW
    vdTTzVpNVpfCChMGqMvr
    VtZzBzhtlrhznFlBfgrfZgFrPjGRMGjRTmSjRjRTHjfRHmRv
    DQpnsbJCsNNnpNNJsDQdCDcRmHPGTHTHRSmRmHjvjHSpSS
    cQbnQdNLdJJQJJJDJWnFwzgwBthrgZBwBgFLZV
    VhRRgmhpFjFFBDVPGPWQPzvvMMWfjf
    qcnbnCbfLqJrCnrcdbbLrGSlzWsQvsWWzvWGdMWGQl
    bnfbwrcwCrHqnHcZhFBTBVRDFmpBHB
    lrtqltJJJqSTWJqVHRnsRhphdbfbzBdhsRsd
    vSZCgZMMLSNvCQLPLDPNgZgnznzBfsGGnQnQGdnsfhsfzb
    CMFLgmPgFFNMFDDCgLLcrWrjTTjtmSJqlWTTwWSr
    LdjljBdZMFdZFLLLgPvWzQRzCsCmCVssmFSW
    TJttwDhnnTlWsQzSQQDvWm
    HtcnfctJwtwrHhrwhfHhJpjNLMZBMgZLrBlbbLNPNj
    qqhNchPdpqTTNqpDmmvvGzVfzfmvdH
    cwccjsFwFjnwGwQDfVVVVv
    FbWjcRsLLFngBrjpbJqCJZTbJZNClq
    lhznMTSzSnjhQGtVPQBdGB
    msfNDDJLWslJgfNgCrmLdtGQFVvdGQPZVttBFP
    RCrJJJDrJRsfgmbsrNsrlDMTSMHcjqwzScjMqqTjbbSc
    nNgsvNWDRvgnRNVCFddTNZTNZQCTFZ
    lffHJfHSPmSfvLlbLmpZrCTFTtrTQHqtTrCCrq
    cpzblplpbvMzWnsDDB
    CgtvQvJvMtWttvwftCdWvDQrfsFcrqnlcnqZZFRcRqsnhF
    HzLzVBNLjHqnhzFlWFlr
    NmBjLbVVbmbTLpTjBNVLHNdCtTSWQvCgdwSwJtWQwdSD
    lncHcnlccVSLNSQNslncLcrZJCrgPfJZDrggJCCvZPHC
    jRqqRmmqFwRFppfPPppPBfpWBvZf
    wmMqjtTdjFwGGdtNhQbVfhntcNLVbL
    HFBgMjpbpddMpbHdgHLLRNwhwFLDtNSRDLLD
    zsCnfqZflrlnhhrtwNgggNNL
    CGqnQzlqlWWMWgVBGg
    pQnvzjztpzpCmtzzjzpnBHrJNGlqggMMqgqlNWgfNNqNCP
    sVTSwddRRDVShwRwRTWgPNqMGQMGNqMWslsg
    hDSTQhcQcHrtcBmZHv
    QRmQfvQpWpswfZWWvNbhlMglgFbZDldlbL
    rzHqtcnqqVjqjGcHdLdFdCFdCbLDnMCh
    DGcGGSPDpWTsSfpv
    llfMHTmvHlfZlFZRzgQzsFBLtLzFGF
    wrWNJrdJhRmhGmNh
    DWrrJjwPjCdPDwdmwnrTZnZZcqZfnqbvZfHvql
    mPmVJJmNZJmlVBPPrZpWcFWbGWbjgqNbdqjSjg
    nMhzwRhwvhMDMgWHRdGHgccggd
    sMvnhQshMwwvdvMMCwBtlZtplZpTmPBVZVlC
    ltlRzpncRglplzhFwFwzZZMWLWZBqnDVZLDVZQQQ
    SJcdvJscNSsGcSGCSJmsTQDTVZQTLTQQWCTTMQCW
    sPdJmcvsJvGJmdJmfpwftfrlztrRlPfP
    LdPrWcMCWCfPdMJgdFsbRRHsRSHRHHcFpH
    hVVTQmQTnRFLFsmzps
    qthVVwZqlQLQhNttDDDWrffDJJJDrgNP
    BTjTNjtlPrBjjrljbnMFfhVWFFhlMWMfHdll
    mzcgZvDggDDCJCZLvsLJLcmVqWVSVqFLfdHHMWVWWWffnF
    cmmcmzQDZQJmZCnDRgQCTTwjpTtwRjrbNjpPpwrj
    rMbchQphhCSbGnzSbl
    qFtgvTTqFFFFJGzWJG
    NZjGqGBNjNHQrhpPNHQr
    LnLmbtTtTwtLcVfFFLtPrfPrfqqqsqhSvrhrhh
    BzJWzZRZJzJvlZJCZgZZpJHCqQDhNQPDqDDrjNsjPPNrhSCN
    lpJWZzJpgHWdWMgHlJMZzgpJLGVLGGvwVwtmcGbvGMGVvncn
    WdBgdqRgWqHmNNwsGgcQ
    ptPVbPbSbMJrmsVzRzhwmcGQ
    SSbvrJbJtCDZfTqdRfCdBZ
    WDNNWvPpvNJRRbGLsGMnnbmG
    qgFdBwgVdjwdtjjdBgMgGmLQsnZrnZssGswsmLrw
    FqTCTtqjdjVqgCqSMJMTvPThTJMNDh
    brSSSpZjVVWdfVrHPhRBggNNGwHr
    fDlLzFCLMvnMMJLNHNCBQwNhRgwRPP
    MJqMmfzDvFtLDtmsVsZZTsSScWcsbq
    CSZlllhSdnDrrDdJjqjzbSGzGvwbfHMb
    gTNvVNLQtsFpQHqfwfBfVMfHzf
    QgNmWTtmTcmmdmrZRvnlPl
    SmzfvfjvjbjLNJjD
    cFhWMhGHTPhccMQQGBTFGwbVVwdbddJDvVJLvDDHvd
    GFMBGcWTWhcGrhFZTTchQsSfgtmnnRvnmnnRgRCrRS
    ZTQHVZsZSQpQQGBMGqfBRbRB
    CwtLDtNFcPnllwnqvMgbvGVfVfBG
    VClLWWFPPhlhctsTrrSpWpmszjZj
    HChzPltNnnHtnpqSpHpFpSfSvS
    mJmQssZJLdTQLcbjlGLGfSgMbqwwSFSMSFMMqMwS
    JdBLlGTjLjjjjdmmBPnRzCBRNPhPtPWPtr
    FPLHMHqqPMgFLLggsMghTJhwtDSSJDltJvtwdvST
    WQfmjQZsjfZNQCrZCNZQQWQBCSClvdwTSClwSwlTJvtwJdbT
    BpmzrWcpBrfmpsGPGFqPRgzqVPLM
    bHjccpHwGHJTfPlffPwr
    VtChMZVhhStZdfTCfJvcPRCTJn
    sLNLZcdNZZqZqqVqSNWtjQDGHssHGHgQHDBgmsDg
    CdWgCpddwgClFlmmVTBbRtRtbntBVVds
    vcJGhPLPhJvChLhMLfccrvfvsVVbsGBTTBnVbRzBVstsGnbz
    vJJHhjcCLPPjQPHLrSZmpgmqwlWZZgZZQm
    VpTFCFtrjCdJdjHVFnSjszSllDjsDzgvzl
    fhmhZBMtfZfGBNfNcmsbZnzSlRsRggslsbnv
    qPLPhMcLhPfNWPpFrdFdFTtJ
    nlgQJhJFlncMzMWZMFvw
    mDdsDfHjHsjHdjTLfpDsbDcNzzwcRbZNZPMcCPWMRPMc
    sqqdwffHjTmdmpffmLddTTGDnSJtJBShVVhrGVJtShrlBBnJ
    CrcMcMDBCmLlZdSd
    qPjGjnQPqWjgZmTdlFwTmqLJ
    bnPnnzHjbPznzVpdpVDcvprr
    TCScMQcQCrssJPQhQs
    VpfnqqfdVVwpqvqwGbDPPsjgPShDSsJhlnSl
    qffdmGpfwfbfvVqpfwwfbdqRMTSTWNMWTmZLTzTCZTMLWC
    QQPpPbPbDNplSJrCCj
    VdMzffgnRmVdfVWRvlrCTjRlNBvrrlrr
    mGgNdthhGgMWWtsFcHcHwqLqtH
    HrPFVqVppVpDjFDrVbCpDFJSLsmwjhjGLLmthJLJLmZs
    WWgRdMdRMnQnRzWvPSssPWssJmhsshtG
    MnfvMlnQccvfMlcTRMQdRfpHPDDDpPrDTbTBNbHbHDCq
    GWWRsSwLhWsRsSbsPttThZqrNBJJBgPNCJCCqNMNgP
    HpVDTHzfFDpFfzHzFVcrBZCggMJBvNrNgcNNrM
    jlVpVpVVQDHdFVlmmmQTlzpjjGstWLsSbsnnnStWLRhnht
    prLMDDjNCLZbdFLGngdLBv
    VQHmhWSSzhWHmPJRJhSmVHJPFvgTbtnTbBtGqbQnbdqgTFqG
    RzwzzhwhwNCvvfpc
    wQgmZnhmWVtwQmnnnQbQhzwsFcRPrFPvRJhPlPPBBBFvJv
    DdjqMMGLLMMGqTGdMqdMLdBBJsJPJBJJrrBFcqRlPlqr
    DsddsfMsWgfzftZb
    lcqlFSFwBBPlNwPlvSlQfWsVLTQjzjWVfLsWVq
    HMMbMHMtJHgFzzFrVVtfFQ
    RMFpCDDFcCNBcZvP
    gwDrClhppDDPwPhnmPlwDrlDjMFfMTjMTjJmRHHJBJRMJHGj
    LbbZBSvSLVRHffHJHJGZ
    WztdtLsSvNQStbbtzdStthWhnwnPBDclgwwnrwllCC
    MnMMBppMBDWMhpnCDBgCBmRbstvPvvbGltSPVGlVPWVv
    TrrddJHjNcTqrrqdFcqZwSvLSlGGPbtFRbLvFVSRPG
    JTccqTcwNQcTJrZwNJcJqHwJBQDfhCBCpCQpmpDfMRfCfBpn
    njVcjHfGjVjpTCpMWprW
    tsSsQDvSqQshDhtmWpnQnmMmbrpdzM
    FNhsDDLNLnNllBqfRJGBVHBPHRRBZZ
    hFVdlFSFlfZdRhgWgdWnnnfGpMNfnLMQzQQjMD
    RsrJRHsvBcvHBHjDMMpDQDjjzDHj
    BcCmBqvrbbqJgmFZtWdRVSVV
    FzzdDJrJCFSFRqLlwsgspsBCpL
    HQdWhMZMVwqLMllw
    bbQtcvZcmHtNPZcWthWRvrdrRzrSDfRSrzjJjR
    bTFZzHjZNJHzLggsJgbdsWcdcShWCwsSSdvGvv
    VDBmntntfCBGGGGhRc
    fMnnPDfmDlmnMPmtmttnVlHzZzNLbFbLbhzJJjMgJFbb
    GzgJGPRrMSgTgpgH
    hcvWhBdhcfPFvmFQvwfbHMsMMbpDpTDSSHsHpd
    LmcvFFlcWQlFlfPnRZPVCJzJClCz
    DdCHCHrmHRgghTHH
    pFVZFwfssMsgghML
    tSnphvhtctSSQNDqNdmrWGvq
    rqmtRmGmcWrRRQprRRnfbGMMlPGGPblwMbTP
    BHHhVZSvDNdhvBVhshbzfPbTmDfnwPwzPgbl
    ddvBsSSdsLdshLsLpmWqcWrCCrtFpQ
    wZPCwdPCHrnLQCGZDcPRqllzqqBzjlqc
    gMmgnJspsvTmWNVWNpTNWNcDcqVjqDcclhSzllRSDzqR
    JnWsgMnngmttFWWMdrwCZwHfZfdfGdFd
    wwgNgrsWvbfBrqqsWbjDCDDDCDCmFbSmLDLlSC
    QdpdzQTVdzRMTVVzcHTQLnlFmZHPSChCmlDPPHnP
    dRMLVttzzVtTVQVqrrrgBtsvWWwtNw
    vtBvntlqMvfnTfPDPhdRNbhdTFzF
    QLWcmrrcgmCgCcsgcQWlWWrrDjjzzjsdFDdRhPNDhhhzDzPF
    GGWHcCQcCCSlmmBVMGVBfMqwJtqv
    cfqfhDRwhqZgRgRzRvcfhBSrsBnrDBBJWrnrWrSmmr
    VCTVjGCTCjFddQntmrsVsJvrtrrW
    PFQGpFbvPRMNqgRq
    MmDgZZGMjZGfZRFztzCtCjzSrF
    cBNpPJpBdNntcBHBccJlsSVVzzSwlCRrCnzsFw
    PPBJLPPBBLPHBNQgqfMQtmTftGGvhq
    bbZnbnVVgVSnbgZtntZrltsprpMCJvpqdJmsCMMmvvCq
    BjDcjLLDzNjLDcjDzhcDNLLLHdpmpHJsqsMMNfCHfJpspqvp
    dFLTFBcBzjFLgTbQtRgTVTlZ
    nqNnrBRjLnjLZCqGGlqSGWlWDS
    mTJTTcTJJfJfhhhwMbQDPWCQFCRlbDCSDDPl
    dhMcRgJmgRrBrrrNgrLZ
    GvJvJSGZFrGmmbmCrWnhjncLctcWttVqjLBB
    wDlTzwlHTncRTqnRBt
    gspglgzDzdPDfpgfdDzsgMPGvZJBrrbZGJNFmCFvmFvFvM
    RLjMZZzfvNLBdjQfBfQdhRfSTVlcVqGbGcLGlbmqLVccmm
    FggHCwsggrWWtCHJDDHtWrTNNlqSnlTlnGVTmWGcbcbm
    PtpttrwsJssPsdRQvphZNzdMBh
    NqqpZBHqTBpPNpPpGwwMPGTJjjLjQljGmtLfftllbJQfGf
    nHczcrSFnVWSlrltrgJmjLLQ
    SvzcDDVVFzdzhndCFSvnhcDspRDRDMPpMHRNPPZqppwM
    FRSbVCSFFCDMFjRMjSSVFSWggMmWtWngJWttWmmJctnt
    BPwcQQcQqQmWHfgrfwrh
    PPlBQdNQvdLzzvclczdNRSbpLSbRbDjFZFFZVsFs
    wtrrVhBbpcZSSjBfSfmm
    MDWTvTMGMRCDCTQWsvfrRjjFfHlFmjlmLlHl
    gQrTQvQDssdNWGsTstcbptwVPqcbpNqttP
    jtGSwGQczrzjtGzrcsJwMRqMVMwRVMWFvVTWFV
    DhLgnDLndDHmLvWqpTHqHHVNqF
    LhdmPhfgZnZDlPCPmDfljQtGsJtBsWjGJzJSWBjC
    zHDjcjBjTfjjfGpf
    NNFTnNwPNNdqnJdFnqqTgmgftfftrWCZGbmrpWttmW
    FLJqVNVNhnwnTRsRQBlQzShs
    HJGJGJzzHHQHfJHsnNsGMbccMrTgbr
    vddSCCjdmVvDDmvmBVbbBchcrrcscMTTnscn
    VjWdFCVVMWWmjdSVFSVpqwwZttfJJltJZqltppLw
    SnmPBPBnMLnPBsSgSDqRNRRccDfNcNQQRg
    lZVWtWVzCjvZzCCGzDwbwRwtqJwJNTtDfD
    zCzHZFFFfdLnBfFf
    NRBFpNNJgNbWbJLRpRbWNtNpZllCZdjjZfjPVljTVCZQltlV
    sDqHmsHcDrwHhMDlfCQfVBjDPCTd
    MnGGcqwhhsrchcmGpzRJSSGGJWJzbJBR
    LBzjQQzcjWvHWLnVDdnHRffHDCVR
    rmJSrPJJsbNZssGSPrFpddfGwDRRpVjVpCGdCp
    mPsrmsNTrPNLTjQlWQhqLc
    MSDFszbhbRRTRdwhtw
    PWmCZCmZVvGqMcjmJRpdTTtdLpqwdlll
    MWGmmCVHvMSBDSNbbHHS
    mBwSBSfSPHZCLPZSWwfPppTndVpdVncFgcgPpP
    rhQJjzQjrltJzGqCrGJTvgqRpnTgFgcFTVFqFR
    QbhGMJrhzrhQGQCsjwMDNWBDSZBZwSBLmM
    bQfDPgDQbQNGPgflWfvMZcRMMFmcvMfZ
    BLqSssjnzpBwszqwzFCNMvzWvRvzCCZFvc
    BpNpjnNHnSpssqLqrBLLHjdhTPPDgbggllldhTPdrrQh
    CvCMqNWVVqqPvNvvChhhdSnFHwBdWwhfdS
    gqTZGGjlmclrZjlmSndSDwfFhhDBHm
    tRrZpgrcctbbltRpgtqCVJCvPsRvsvPCQPCLPz
    dTjRdWDBRzvjfzTfvTJPtJttsSLqHsSQJw
    hrJNmbnFNZrbhlCsqltqcQcQSQqwPL
    pVNhFgZphZmjzvjGDJWzVJ
    gWzQhCWbQnCCFgCJnFQnWCzwjrHjjHGTwHGrhLwjjjtStL
    splcpqDNDqqcZqRlspwHbjVrjjHTrwSbtVNV
    BpMslDqDmRDRsBRBJPPnbzCfvQgmCWJb
    tRtgRQWCwlTglHZHTglCtTdbbfvhWpbSBbhWzzbfGpfhbb
    cqZVMJmLqmNrsJMDbzGrGSvzGBhvvG
    mmnJPMZcclFRdnQtCQ
    QVQVqfFzVVQQrQwZsCTrBtTrccTtctcJRRjT
    vNNPnvGbBtWBLvBf
    mMHbfDfHdHGmnhDDqZFDzVSQzF
    NNlTNFCRTrfllTZsPWSsFPfzJdVQVpDQVszQVtpbzJMVbJ
    LNHjNHjmLLjNqvGgvVQJQDVLbDVDpdQQzQ
    nqmqGHjwgHvgwGHjGgccNTSWrrlCZrFfPSFFCP
    qWzCQqhPCHjHmqJhqvqmjRgSFMTFggMFTFVRVVTgTm
    SptGsDlnGfnDLgTMTwgRFFFs
    bBcntZdpGZZcctlGtDfnDnBCSWqJvQhqjqzjhJqJQCQWPd
    SjZJrSSDShddqLvPqzzdwq
    nTssfRpQQmQCHlPBBgGmwVGzwm
    TWQsbCRQFHFWQRTpzRHRsRrMtDrjhjbtMbccrttJjJht
    cCChVMwPPMHCPCCPrvJnntdTJSvTtdrSRt
    FGfFDBhGGlfGGfWJWdbSRSnRNbTvdn
    fGpGlDmBhflgfDFmmfFpcVzMzqZZzcCPQVZzqP
    SmgtSjGPjppBjbqqWTCZDQTHHHTg
    VsFfCzLvsMfzNfNRhVMslzlHqrWrQDQcqDqTrLWHcrWJJW
    dsRdsNCvNMVpwPdnnGbbPb
""".trimIndent()
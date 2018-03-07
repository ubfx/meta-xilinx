SUMMARY = "Control Software for VCU"
DESCRIPTION = "Control software libraries, test applications and headers provider for VCU"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=03a7aef7e6f6a76a59fd9b8ba450b493"

BRANCH ?= "master"
REPO   ?= "git://github.com/xilinx/vcu-ctrl-sw.git;protocol=https"
SRCREV = "d703e4ae075a65082a8fb2041082a36b39d84a25"

BRANCHARG = "${@['nobranch=1', 'branch=${BRANCH}'][d.getVar('BRANCH', True) != '']}"
SRC_URI = "${REPO};${BRANCHARG}"

S  = "${WORKDIR}/git"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE_zynqmp = "zynqmp"

PACKAGE_ARCH = "${SOC_FAMILY}"

RDEPENDS_${PN} = "kernel-module-vcu"

EXTRA_OEMAKE = "CC='${CC}' CXX='${CXX} ${CXXFLAGS}'"

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}/vcu-ctrl-sw/include

    install -Dm 0755 ${S}/bin/AL_Encoder.exe ${D}/${bindir}/AL_Encoder.exe
    install -Dm 0755 ${S}/bin/AL_Decoder.exe ${D}/${bindir}/AL_Decoder.exe

    oe_runmake install_headers INSTALL_HDR_PATH=${D}${includedir}/vcu-ctrl-sw/include
    oe_libinstall -C ${S}/bin/ -so liballegro_decode ${D}/${libdir}/
    oe_libinstall -C ${S}/bin/ -so liballegro_encode ${D}/${libdir}/
}

# These libraries shouldn't get installed in world builds unless something
# explicitly depends upon them.

EXCLUDE_FROM_WORLD = "1"

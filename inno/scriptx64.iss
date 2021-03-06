; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!
; ------------------------------------
; Christian Klugesherz
; Don't Forget to run : launch4j
; To create .exe    Build --> Compile

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{A01E523A-B30C-4082-ADE1-C8407CAF44B9}
AppName=PAC-Tool
AppVersion=Alpha 0.3.2
;AppVerName=PAC-Tool Alpha 0.1
AppPublisher=Christian Klugesherz
AppPublisherURL=https://github.com/ckl67/PAC-Tool
AppSupportURL=https://github.com/ckl67/PAC-Tool
AppUpdatesURL=https://github.com/ckl67/PAC-Tool
DefaultDirName={pf}\PAC-Tool
DefaultGroupName=PAC-Tool
OutputDir=D:\Users\kluges1\workspace\pac-tool\exe
OutputBaseFilename=pactoolx64_setup
SetupIconFile=D:\Users\kluges1\workspace\pac-tool\src\gui\images\PAC-Tool.ico
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "french"; MessagesFile: "compiler:Languages\French.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "D:\Users\kluges1\workspace\pac-tool\inno\PAC-Toolx64.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "D:\Users\kluges1\workspace\pac-tool\inno\jrex64\*"; DestDir: "{app}\jrex64"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "D:\Users\kluges1\workspace\pac-tool\ressources\*"; DestDir: "{app}\ressources"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\PAC-Tool"; Filename: "{app}\PAC-Toolx64.exe"
Name: "{group}\{cm:UninstallProgram,PAC-Tool}"; Filename: "{uninstallexe}"
Name: "{commondesktop}\PAC-Tool"; Filename: "{app}\PAC-Toolx64.exe"; Tasks: desktopicon

[Run]
Filename: "{app}\PAC-Toolx64.exe"; Description: "{cm:LaunchProgram,PAC-Tool}"; Flags: nowait postinstall skipifsilent


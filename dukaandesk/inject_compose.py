#!/usr/bin/env python3
import os
import re

BRAIN_DIR = "/Users/admin/.gemini/antigravity/brain/b6b6813a-8217-4154-9efd-31196ce08685"
SRC_BASE = "/Users/admin/.gemini/antigravity/scratch/dukaandesk/app/src/main/java"

def process_md_file(md_path):
    if not os.path.exists(md_path):
        print(f"Warning: File not found: {md_path}")
        return

    with open(md_path, 'r') as f:
        content = f.read()

    # Find all Kotlin code blocks
    pattern = re.compile(r'```kotlin\n(.*?)\n```', re.DOTALL)
    matches = pattern.findall(content)

    for i, code in enumerate(matches):
        # Extract package name to determine directory
        pkg_match = re.search(r'package\s+([a-zA-Z0-9_.]+)', code)
        if not pkg_match:
            print(f"Warning: No package found in a code block in {os.path.basename(md_path)}, skipping.")
            continue
        
        pkg = pkg_match.group(1)
        pkg_path = pkg.replace('.', '/')
        dest_dir = os.path.join(SRC_BASE, pkg_path)
        os.makedirs(dest_dir, exist_ok=True)
        
        # Determine the target filename based on the content of the class/file
        filename = "Generated.kt"
        
        # Theme files mapping
        if "val DeepBlue =" in code and "val OffWhite =" in code:
            filename = "Color.kt"
        elif "val DukaanDeskTypography" in code:
            filename = "Type.kt"
        elif "object DukaanDimensions" in code:
            filename = "Dimensions.kt"
        elif "fun DukaanDeskTheme(" in code:
            filename = "Theme.kt"
            
        # Screen component mapping    
        elif "fun QuickPosScreen(" in code:
            filename = "QuickPosScreen.kt"
        elif "fun DashboardScreen(" in code:
            filename = "DashboardScreen.kt"
        elif "fun RestockNeededScreen(" in code:
            filename = "RestockNeededScreen.kt"
        elif "fun ScheduleScreen(" in code:
            filename = "ScheduleScreen.kt"
        elif "fun OfflineModeWrapper(" in code:
            filename = "OfflineModeWrapper.kt"
        else:
            filename = f"Component_{i}.kt"

        dest_file = os.path.join(dest_dir, filename)
        with open(dest_file, 'w') as out_f:
            # Drop the extracted Kotlin code precisely into the .kt file
            out_f.write(code.strip() + "\n")
        
        print(f"Successfully injected: {dest_file.replace(SRC_BASE, '.../src/main/java')}")

def main():
    print("Starting Injection of DukaanDesk Compose files...")
    md_files = [
        "dukaandesk_compose_theme.md",
        "quick_pos_screen.md",
        "dashboard_screen.md",
        "inventory_action_screen.md",
        "dispatch_screen.md",
        "offline_wrapper.md"
    ]

    for md in md_files:
        process_md_file(os.path.join(BRAIN_DIR, md))
        
    print("\nAll Jetpack Compose screens have been extracted and ported into your Java/Kotlin directory mapping!")

if __name__ == "__main__":
    main()

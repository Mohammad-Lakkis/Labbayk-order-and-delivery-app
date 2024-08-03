using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Labbayk.Migrations
{
    /// <inheritdoc />
    public partial class districtToLoc : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "District",
                table: "Locations",
                newName: "Loc");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Loc",
                table: "Locations",
                newName: "District");
        }
    }
}

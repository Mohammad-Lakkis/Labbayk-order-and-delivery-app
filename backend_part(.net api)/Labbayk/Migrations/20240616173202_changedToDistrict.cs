using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Labbayk.Migrations
{
    /// <inheritdoc />
    public partial class changedToDistrict : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "MyProperty",
                table: "Locations",
                newName: "District");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "District",
                table: "Locations",
                newName: "MyProperty");
        }
    }
}

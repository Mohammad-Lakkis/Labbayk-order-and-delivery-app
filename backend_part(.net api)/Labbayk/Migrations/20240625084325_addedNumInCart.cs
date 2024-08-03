using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace Labbayk.Migrations
{
    /// <inheritdoc />
    public partial class addedNumInCart : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "NumberInCart",
                table: "Items",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "NumberInCart",
                table: "Items");
        }
    }
}

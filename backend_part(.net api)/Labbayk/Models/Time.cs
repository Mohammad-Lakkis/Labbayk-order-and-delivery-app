using System.ComponentModel.DataAnnotations;

namespace Labbayk.Models
{
    public class Time
    {
        [Key]
        public int Id { get; set; }
        public string Value { get; set; } = string.Empty;
        public ICollection<Item> Items { get; set; }
    }
}
